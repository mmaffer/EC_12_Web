import { useEffect, useState } from 'react'
import api from '../api/client.js'

const BitacoraPage = () => {
  const [registros, setRegistros] = useState([])

  const fetchBitacora = async () => {
    const { data } = await api.get('/api/admin/bitacora')
    setRegistros(data)
  }

  useEffect(() => {
    fetchBitacora()
  }, [])

  return (
    <section>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="mb-0">Bitácora</h2>
        <button className="btn btn-sm btn-outline-secondary" onClick={fetchBitacora}>
          Refrescar
        </button>
      </div>
      <div className="card shadow-sm">
        <div className="card-body">
          <div className="table-responsive">
            <table className="table">
              <thead>
                <tr>
                  <th>Fecha</th>
                  <th>Usuario</th>
                  <th>Acción</th>
                  <th>Detalle</th>
                </tr>
              </thead>
              <tbody>
                {registros.map((registro) => (
                  <tr key={registro.id}>
                    <td>{new Date(registro.fechaHora).toLocaleString()}</td>
                    <td>{registro.usuario}</td>
                    <td>{registro.accion}</td>
                    <td>{registro.detalle}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>
  )
}

export default BitacoraPage

