import { useEffect, useState } from 'react'
import api from '../api/client.js'

const VentasPage = () => {
  const [facturas, setFacturas] = useState([])
  const [estados, setEstados] = useState([])
  const [filtros, setFiltros] = useState({ inicio: '', fin: '' })

  const fetchFacturas = async (params = {}) => {
    const [facturasRes, estadosRes] = await Promise.all([
      api.get('/api/facturas', { params }),
      api.get('/api/catalogos/estados-factura'),
    ])
    setFacturas(facturasRes.data)
    setEstados(estadosRes.data)
  }

  useEffect(() => {
    fetchFacturas()
  }, [])

  const handleFilterChange = (event) => {
    const { name, value } = event.target
    setFiltros((prev) => ({ ...prev, [name]: value }))
  }

  const aplicarFiltro = (event) => {
    event.preventDefault()
    if (filtros.inicio && filtros.fin) {
      fetchFacturas({ inicio: filtros.inicio, fin: filtros.fin })
    } else {
      fetchFacturas()
    }
  }

  const actualizarEstado = async (facturaId, estado) => {
    await api.patch(`/api/facturas/${facturaId}/estado`, { estado })
    fetchFacturas({ inicio: filtros.inicio, fin: filtros.fin })
  }

  const totalDia = facturas.reduce((acc, factura) => acc + (factura.total ?? 0), 0)

  return (
    <section>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2 className="mb-0">Ventas</h2>
          <p className="text-muted mb-0">Control de facturación y métodos de pago.</p>
        </div>
        <div className="text-end">
          <p className="mb-0 text-muted">Total listado</p>
          <h4 className="mb-0">S/ {totalDia.toFixed(2)}</h4>
        </div>
      </div>
      <form className="row g-3 mb-3" onSubmit={aplicarFiltro}>
        <div className="col-md-4">
          <label className="form-label">Desde</label>
          <input type="date" className="form-control" name="inicio" value={filtros.inicio} onChange={handleFilterChange} />
        </div>
        <div className="col-md-4">
          <label className="form-label">Hasta</label>
          <input type="date" className="form-control" name="fin" value={filtros.fin} onChange={handleFilterChange} />
        </div>
        <div className="col-md-4 d-flex align-items-end">
          <button className="btn btn-outline-primary me-2" type="submit">
            Filtrar
          </button>
          <button
            className="btn btn-outline-secondary"
            type="button"
            onClick={() => {
              setFiltros({ inicio: '', fin: '' })
              fetchFacturas()
            }}
          >
            Limpiar
          </button>
        </div>
      </form>
      <div className="card shadow-sm">
        <div className="card-body">
          <div className="table-responsive">
            <table className="table align-middle">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Pedido</th>
                  <th>Fecha</th>
                  <th>Total</th>
                  <th>Método</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                {facturas.map((factura) => (
                  <tr key={factura.id}>
                    <td>{factura.id}</td>
                    <td>{factura.pedidoId}</td>
                    <td>{new Date(factura.fechaEmision).toLocaleString()}</td>
                    <td>S/ {factura.total?.toFixed?.(2) ?? factura.total}</td>
                    <td>{factura.metodoPago}</td>
                    <td>
                      <select
                        className="form-select form-select-sm"
                        value={factura.estado}
                        onChange={(event) => actualizarEstado(factura.id, event.target.value)}
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
    </section>
  )
}

export default VentasPage

