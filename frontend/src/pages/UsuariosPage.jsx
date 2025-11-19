import { useEffect, useState } from 'react'
import api from '../api/client.js'

const UsuariosPage = () => {
  const [usuarios, setUsuarios] = useState([])
  const [roles, setRoles] = useState([])
  const [form, setForm] = useState({ username: '', password: '', rol: '' })

  const fetchData = async () => {
    const [usuariosRes, rolesRes] = await Promise.all([
      api.get('/api/admin/usuarios'),
      api.get('/api/catalogos/roles'),
    ])
    setUsuarios(usuariosRes.data)
    setRoles(rolesRes.data)
    if (!form.rol && rolesRes.data.length > 0) {
      setForm((prev) => ({ ...prev, rol: rolesRes.data[0] }))
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  const handleChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    await api.post('/api/admin/usuarios', form)
    setForm((prev) => ({ ...prev, username: '', password: '' }))
    fetchData()
  }

  return (
    <section>
      <h2 className="mb-4">Usuarios del sistema</h2>
      <div className="row g-4">
        <div className="col-lg-7">
          <div className="card shadow-sm">
            <div className="card-body">
              <table className="table">
                <thead>
                  <tr>
                    <th>Usuario</th>
                    <th>Rol</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  {usuarios.map((usuario) => (
                    <tr key={usuario.id}>
                      <td>{usuario.nombreUsuario}</td>
                      <td>{usuario.rol}</td>
                      <td>
                        <span className={`badge text-bg-${usuario.activo ? 'success' : 'secondary'}`}>
                          {usuario.activo ? 'Activo' : 'Inactivo'}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div className="col-lg-5">
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">Nuevo usuario</h5>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label className="form-label">Usuario</label>
                  <input className="form-control" name="username" value={form.username} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Contraseña</label>
                  <input
                    type="password"
                    className="form-control"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="form-label">Rol</label>
                  <select className="form-select" name="rol" value={form.rol} onChange={handleChange}>
                    {roles.map((rol) => (
                      <option key={rol} value={rol}>
                        {rol}
                      </option>
                    ))}
                  </select>
                </div>
                <button className="btn btn-primary w-100">Crear usuario</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default UsuariosPage

