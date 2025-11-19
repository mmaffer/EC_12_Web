import { useEffect, useState } from 'react'
import api from '../api/client.js'

const initialForm = {
  dni: '',
  nombres: '',
  apellidos: '',
  telefono: '',
  correo: '',
}

const ClientesPage = () => {
  const [clientes, setClientes] = useState([])
  const [form, setForm] = useState(initialForm)
  const [loading, setLoading] = useState(false)
  const [message, setMessage] = useState(null)

  const fetchClientes = async () => {
    const { data } = await api.get('/api/clientes')
    setClientes(data)
  }

  useEffect(() => {
    fetchClientes()
  }, [])

  const handleChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)
    try {
      await api.post('/api/clientes', form)
      setMessage('Cliente registrado correctamente.')
      setForm(initialForm)
      fetchClientes()
    } finally {
      setLoading(false)
    }
  }

  const toggleEstado = async (cliente) => {
    await api.patch(`/api/clientes/${cliente.id}/estado`, null, { params: { activo: cliente.estado !== 'ACTIVO' } })
    fetchClientes()
  }

  return (
    <section>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2 className="mb-0">Clientes</h2>
          <p className="text-muted mb-0">Registra y gestiona a tus clientes frecuentes.</p>
        </div>
      </div>
      {message && <div className="alert alert-success">{message}</div>}
      <div className="row g-4">
        <div className="col-lg-7">
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">Listado</h5>
              <div className="table-responsive">
                <table className="table align-middle">
                  <thead>
                    <tr>
                      <th>DNI</th>
                      <th>Cliente</th>
                      <th>Teléfono</th>
                      <th>Estado</th>
                      <th />
                    </tr>
                  </thead>
                  <tbody>
                    {clientes.map((cliente) => (
                      <tr key={cliente.id}>
                        <td>{cliente.dni}</td>
                        <td>
                          <strong>{cliente.nombres} {cliente.apellidos}</strong>
                          <br />
                          <small className="text-muted">{cliente.correo}</small>
                        </td>
                        <td>{cliente.telefono}</td>
                        <td>
                          <span className={`badge ${cliente.estado === 'ACTIVO' ? 'text-bg-success' : 'text-bg-secondary'}`}>
                            {cliente.estado}
                          </span>
                        </td>
                        <td>
                          <button
                            className="btn btn-sm btn-outline-primary"
                            onClick={() => toggleEstado(cliente)}
                          >
                            {cliente.estado === 'ACTIVO' ? 'Inactivar' : 'Activar'}
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div className="col-lg-5">
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">Nuevo cliente</h5>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label className="form-label">DNI</label>
                  <input className="form-control" name="dni" maxLength={8} value={form.dni} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Nombres</label>
                  <input className="form-control" name="nombres" value={form.nombres} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Apellidos</label>
                  <input className="form-control" name="apellidos" value={form.apellidos} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Teléfono</label>
                  <input className="form-control" name="telefono" value={form.telefono} onChange={handleChange} />
                </div>
                <div className="mb-4">
                  <label className="form-label">Correo</label>
                  <input type="email" className="form-control" name="correo" value={form.correo} onChange={handleChange} />
                </div>
                <button className="btn btn-primary w-100" disabled={loading}>
                  {loading ? 'Guardando...' : 'Registrar'}
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default ClientesPage

