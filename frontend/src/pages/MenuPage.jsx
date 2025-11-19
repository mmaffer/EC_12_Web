import { useEffect, useState } from 'react'
import api from '../api/client.js'

const MenuPage = () => {
  const [platos, setPlatos] = useState([])
  const [tipos, setTipos] = useState([])
  const [estados, setEstados] = useState([])
  const [form, setForm] = useState({
    nombre: '',
    tipo: '',
    precio: '',
    descripcion: '',
    estado: 'ACTIVO',
  })

  const fetchData = async () => {
    const [platosRes, tiposRes, estadosRes] = await Promise.all([
      api.get('/api/platos'),
      api.get('/api/catalogos/tipos-plato'),
      api.get('/api/catalogos/estados-registro'),
    ])
    setPlatos(platosRes.data)
    setTipos(tiposRes.data)
    setEstados(estadosRes.data)
    if (!form.tipo && tiposRes.data.length > 0) {
      setForm((prev) => ({ ...prev, tipo: tiposRes.data[0] }))
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
    await api.post('/api/platos', { ...form, precio: Number(form.precio) })
    setForm({ nombre: '', tipo: tipos[0] ?? '', precio: '', descripcion: '', estado: 'ACTIVO' })
    fetchData()
  }

  return (
    <section>
      <h2 className="mb-4">Menú</h2>
      <div className="row g-4">
        <div className="col-lg-7">
          <div className="card shadow-sm">
            <div className="card-body">
              <table className="table">
                <thead>
                  <tr>
                    <th>Nombre</th>
                    <th>Tipo</th>
                    <th>Precio</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  {platos.map((plato) => (
                    <tr key={plato.id}>
                      <td>{plato.nombre}</td>
                      <td>{plato.tipo}</td>
                      <td>S/ {plato.precio?.toFixed?.(2) ?? plato.precio}</td>
                      <td>
                        <span className={`badge text-bg-${plato.estado === 'ACTIVO' ? 'success' : 'secondary'}`}>
                          {plato.estado}
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
              <h5 className="card-title">Nuevo plato</h5>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label className="form-label">Nombre</label>
                  <input className="form-control" name="nombre" value={form.nombre} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Tipo</label>
                  <select className="form-select" name="tipo" value={form.tipo} onChange={handleChange} required>
                    {tipos.map((tipo) => (
                      <option key={tipo} value={tipo}>
                        {tipo}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">Precio</label>
                  <input
                    className="form-control"
                    type="number"
                    step="0.1"
                    name="precio"
                    value={form.precio}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="mb-3">
                  <label className="form-label">Descripción</label>
                  <textarea className="form-control" name="descripcion" value={form.descripcion} onChange={handleChange} />
                </div>
                <div className="mb-4">
                  <label className="form-label">Estado</label>
                  <select className="form-select" name="estado" value={form.estado} onChange={handleChange}>
                    {estados.map((estado) => (
                      <option key={estado} value={estado}>
                        {estado}
                      </option>
                    ))}
                  </select>
                </div>
                <button className="btn btn-primary w-100">Guardar</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default MenuPage

