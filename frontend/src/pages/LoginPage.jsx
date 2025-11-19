import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth.js'

const LoginPage = () => {
  const { login, user } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const from = location.state?.from?.pathname || '/'
  const [form, setForm] = useState({ username: '', password: '' })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (user) {
      navigate(from, { replace: true })
    }
  }, [user, navigate, from])

  const handleChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)
    setError(null)
    const success = await login(form.username, form.password)
    setLoading(false)
    if (success) {
      navigate(from, { replace: true })
    } else {
      setError('Credenciales inválidas o usuario inactivo.')
    }
  }

  return (
    <div className="bg-light vh-100 d-flex align-items-center justify-content-center">
      <div className="card shadow p-4" style={{ minWidth: 360 }}>
        <h2 className="mb-3 text-center">Sabor Gourmet</h2>
        <p className="text-muted text-center">Accede con tus credenciales</p>
        {error && <div className="alert alert-danger">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Usuario</label>
            <input
              type="text"
              name="username"
              className="form-control"
              value={form.username}
              onChange={handleChange}
              required
              autoFocus
            />
          </div>
          <div className="mb-4">
            <label className="form-label">Contraseña</label>
            <input
              type="password"
              name="password"
              className="form-control"
              value={form.password}
              onChange={handleChange}
              required
            />
          </div>
          <button className="btn btn-primary w-100" disabled={loading}>
            {loading ? 'Ingresando...' : 'Ingresar'}
          </button>
        </form>
      </div>
    </div>
  )
}

export default LoginPage

