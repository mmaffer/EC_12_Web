import { useEffect, useState } from 'react'
import api from '../api/client.js'

const InventarioPage = () => {
  const [insumos, setInsumos] = useState([])
  const [alertas, setAlertas] = useState([])
  const [proveedores, setProveedores] = useState([])
  const [compras, setCompras] = useState([])
  const [form, setForm] = useState({ proveedorId: '', insumoId: '', cantidad: 1, precioUnitario: 0 })

  const fetchData = async () => {
    const [insumosRes, alertasRes, proveedoresRes, comprasRes] = await Promise.all([
      api.get('/api/inventario/insumos'),
      api.get('/api/inventario/insumos/alertas'),
      api.get('/api/inventario/proveedores'),
      api.get('/api/inventario/compras'),
    ])
    setInsumos(insumosRes.data)
    setAlertas(alertasRes.data)
    setProveedores(proveedoresRes.data)
    setCompras(comprasRes.data)
    if (!form.insumoId && insumosRes.data.length > 0) {
      setForm((prev) => ({ ...prev, insumoId: insumosRes.data[0].id }))
    }
    if (!form.proveedorId && proveedoresRes.data.length > 0) {
      setForm((prev) => ({ ...prev, proveedorId: proveedoresRes.data[0].id }))
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  const handleChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const registrarCompra = async (event) => {
    event.preventDefault()
    await api.post('/api/inventario/compras', {
      proveedorId: Number(form.proveedorId),
      detalles: [
        {
          insumoId: Number(form.insumoId),
          cantidad: Number(form.cantidad),
          precioUnitario: Number(form.precioUnitario),
        },
      ],
    })
    setForm((prev) => ({ ...prev, cantidad: 1, precioUnitario: 0 }))
    fetchData()
  }

  return (
    <section>
      <h2 className="mb-4">Inventario y compras</h2>
      <div className="row g-4">
        <div className="col-lg-6">
          <div className="card shadow-sm mb-4">
            <div className="card-body">
              <h5 className="card-title">Insumos</h5>
              <div className="table-responsive">
                <table className="table">
                  <thead>
                    <tr>
                      <th>Nombre</th>
                      <th>Stock</th>
                      <th>Mínimo</th>
                    </tr>
                  </thead>
                  <tbody>
                    {insumos.map((insumo) => (
                      <tr key={insumo.id}>
                        <td>{insumo.nombre}</td>
                        <td>{insumo.stock ?? 0} {insumo.unidadMedida}</td>
                        <td>{insumo.stockMinimo ?? 0}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
              {alertas.length > 0 && (
                <div className="alert alert-warning mt-3">
                  <strong>Alerta:</strong> {alertas.length} insumos requieren reposición.
                </div>
              )}
            </div>
          </div>
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">Compras recientes</h5>
              <ul className="list-group list-group-flush">
                {compras.map((compra) => (
                  <li key={compra.id} className="list-group-item">
                    <div className="d-flex justify-content-between">
                      <strong>{compra.proveedor?.nombre}</strong>
                      <span>S/ {compra.total?.toFixed?.(2) ?? compra.total}</span>
                    </div>
                    <small className="text-muted">{compra.fechaCompra}</small>
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
        <div className="col-lg-6">
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">Registrar compra</h5>
              <form onSubmit={registrarCompra}>
                <div className="mb-3">
                  <label className="form-label">Proveedor</label>
                  <select className="form-select" name="proveedorId" value={form.proveedorId} onChange={handleChange} required>
                    {proveedores.map((proveedor) => (
                      <option key={proveedor.id} value={proveedor.id}>
                        {proveedor.nombre}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">Insumo</label>
                  <select className="form-select" name="insumoId" value={form.insumoId} onChange={handleChange} required>
                    {insumos.map((insumo) => (
                      <option key={insumo.id} value={insumo.id}>
                        {insumo.nombre}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="row g-3">
                  <div className="col-md-6">
                    <label className="form-label">Cantidad</label>
                    <input
                      className="form-control"
                      type="number"
                      min={0.1}
                      step="0.1"
                      name="cantidad"
                      value={form.cantidad}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="col-md-6">
                    <label className="form-label">Precio unitario</label>
                    <input
                      className="form-control"
                      type="number"
                      min={0.1}
                      step="0.1"
                      name="precioUnitario"
                      value={form.precioUnitario}
                      onChange={handleChange}
                      required
                    />
                  </div>
                </div>
                <button className="btn btn-primary w-100 mt-4">Guardar compra</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default InventarioPage

