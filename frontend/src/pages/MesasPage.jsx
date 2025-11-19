import { useEffect, useState } from 'react'
import api from '../api/client.js'

const MesasPage = () => {
  const [mesas, setMesas] = useState([])
  const [estados, setEstados] = useState([])
  const [form, setForm] = useState({ numero: '', capacidad: 4 })
  const [loading, setLoading] = useState(false)

  const fetchData = async () => {
    const [mesasRes, estadosRes] = await Promise.all([
      api.get('/api/mesas'),
      api.get('/api/catalogos/estados-mesa'),
    ])
    setMesas(mesasRes.data)
    setEstados(estadosRes.data)
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
    setLoading(true)
    try {
      await api.post('/api/mesas', { ...form, numero: Number(form.numero), capacidad: Number(form.capacidad) })
      setForm({ numero: '', capacidad: 4 })
      fetchData()
    } finally {
      setLoading(false)
    }
  }

  const updateEstado = async (mesaId, estado) => {
    await api.patch(`/api/mesas/${mesaId}/estado`, null, { params: { estado } })
    fetchData()
  }

  return (
    <section>
      <h2 className="mb-4">Mesas</h2>
      <div className="row g-4">
        <div className="col-lg-7">
          <div className="card shadow-sm">
            <div className="card-body">
              <table className="table align-middle">
                <thead>
                  <tr>
                    <th>Número</th>
                    <th>Capacidad</th>
                    <th>Estado</th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {mesas.map((mesa) => (
                    <tr key={mesa.id}>
                      <td>{mesa.numero}</td>
                      <td>{mesa.capacidad}</td>
                      <td>
                        <span className={`badge text-bg-${mesa.estado === 'DISPONIBLE' ? 'success' : 'secondary'}`}>
                          {mesa.estado}
                        </span>
                      </td>
                      <td>
                        <select
                          className="form-select form-select-sm"
                          value={mesa.estado}
                          onChange={(event) => updateEstado(mesa.id, event.target.value)}
                        >
                          {estados.map((estado) => (
                            <option key={estado} value={estado}>
                              {estado}
                            </option>
                          ))}
                        </select>
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
              <h5 className="card-title">Registrar mesa</h5>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label className="form-label">Número</label>
                  <input className="form-control" name="numero" value={form.numero} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Capacidad</label>
                  <input
                    className="form-control"
                    type="number"
                    name="capacidad"
                    min={1}
                    value={form.capacidad}
                    onChange={handleChange}
                    required
                  />
                </div>
                <button className="btn btn-primary w-100" disabled={loading}>
                  {loading ? 'Guardando...' : 'Guardar'}
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default MesasPage

