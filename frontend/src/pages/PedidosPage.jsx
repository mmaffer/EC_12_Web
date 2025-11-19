import { useEffect, useState } from 'react'
import api from '../api/client.js'

const PedidosPage = () => {
  const [pedidos, setPedidos] = useState([])
  const [mesas, setMesas] = useState([])
  const [clientes, setClientes] = useState([])
  const [platos, setPlatos] = useState([])
  const [estados, setEstados] = useState([])
  const [pagos, setPagos] = useState([])
  const [items, setItems] = useState([])
  const [nuevoItem, setNuevoItem] = useState({ platoId: '', cantidad: 1 })
  const [form, setForm] = useState({ mesaId: '', clienteId: '' })
  const [mensaje, setMensaje] = useState(null)

  const fetchData = async () => {
    const [pedidosRes, mesasRes, clientesRes, platosRes, estadosRes, pagosRes] = await Promise.all([
      api.get('/api/pedidos'),
      api.get('/api/mesas'),
      api.get('/api/clientes'),
      api.get('/api/platos'),
      api.get('/api/catalogos/estados-pedido'),
      api.get('/api/catalogos/metodos-pago'),
    ])
    setPedidos(pedidosRes.data)
    setMesas(mesasRes.data)
    setClientes(clientesRes.data)
    setPlatos(platosRes.data)
    setEstados(estadosRes.data)
    setPagos(pagosRes.data)
    if (!nuevoItem.platoId && platosRes.data.length > 0) {
      setNuevoItem((prev) => ({ ...prev, platoId: platosRes.data[0].id }))
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  const handleFormChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleItemChange = (event) => {
    const { name, value } = event.target
    setNuevoItem((prev) => ({ ...prev, [name]: value }))
  }

  const agregarItem = () => {
    if (!nuevoItem.platoId) return
    setItems((prev) => [...prev, { platoId: Number(nuevoItem.platoId), cantidad: Number(nuevoItem.cantidad) }])
  }

  const eliminarItem = (index) => {
    setItems((prev) => prev.filter((_, idx) => idx !== index))
  }

  const registrarPedido = async (event) => {
    event.preventDefault()
    if (!items.length) {
      setMensaje('Agrega al menos un plato al pedido.')
      return
    }
    await api.post('/api/pedidos', {
      mesaId: Number(form.mesaId),
      clienteId: form.clienteId ? Number(form.clienteId) : null,
      items,
    })
    setMensaje('Pedido registrado exitosamente.')
    setItems([])
    setForm({ mesaId: '', clienteId: '' })
    fetchData()
  }

  const actualizarEstado = async (pedidoId, payload) => {
    await api.patch(`/api/pedidos/${pedidoId}/estado`, payload)
    fetchData()
  }

  const mesasDisponibles = mesas.filter((mesa) => mesa.estado === 'DISPONIBLE')

  return (
    <section>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2 className="mb-0">Pedidos</h2>
          <p className="text-muted mb-0">Control de pedidos activos y generación de facturas.</p>
        </div>
      </div>
      {mensaje && <div className="alert alert-info">{mensaje}</div>}
      <div className="row g-4">
        <div className="col-xl-7">
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">Pedidos en curso</h5>
              <div className="table-responsive">
                <table className="table table-hover align-middle">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Mesa</th>
                      <th>Cliente</th>
                      <th>Estado</th>
                      <th>Total</th>
                      <th>Acciones</th>
                    </tr>
                  </thead>
                  <tbody>
                    {pedidos.map((pedido) => (
                      <tr key={pedido.id}>
                        <td>{pedido.id}</td>
                        <td>Mesa {pedido.mesaNumero}</td>
                        <td>{pedido.clienteNombre ?? 'Sin cliente'}</td>
                        <td>
                          <span className={`badge text-bg-${pedido.estado === 'CERRADO' ? 'success' : 'info'}`}>
                            {pedido.estado}
                          </span>
                        </td>
                        <td>S/ {pedido.total?.toFixed?.(2) ?? pedido.total}</td>
                        <td>
                          <div className="d-flex flex-column gap-2">
                            <select
                              className="form-select form-select-sm"
                              value={pedido.estado}
                              onChange={(event) =>
                                actualizarEstado(pedido.id, {
                                  estado: event.target.value,
                                  metodoPago: pedido.factura?.metodoPago || pagos[0],
                                  pagado: pedido.factura?.estado === 'PAGADO',
                                })
                              }
                            >
                              {estados.map((estado) => (
                                <option key={estado} value={estado}>
                                  {estado}
                                </option>
                              ))}
                            </select>
                            {pedido.estado === 'CERRADO' ? (
                              <span className={`badge text-bg-${pedido.factura?.estado === 'PAGADO' ? 'success' : 'warning'}`}>
                                Factura {pedido.factura?.estado ?? 'PENDIENTE'}
                              </span>
                            ) : (
                              <div className="input-group input-group-sm">
                                <select className="form-select" onChange={(event) =>
                                  actualizarEstado(pedido.id, {
                                    estado: 'CERRADO',
                                    metodoPago: event.target.value,
                                    pagado: true,
                                  })
                                }>
                                  <option value="">Cerrar y facturar</option>
                                  {pagos.map((pago) => (
                                    <option key={pago} value={pago}>
                                      {pago}
                                    </option>
                                  ))}
                                </select>
                              </div>
                            )}
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div className="col-xl-5">
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">Nuevo pedido</h5>
              <form onSubmit={registrarPedido}>
                <div className="mb-3">
                  <label className="form-label">Mesa</label>
                  <select className="form-select" name="mesaId" value={form.mesaId} onChange={handleFormChange} required>
                    <option value="">Seleccione</option>
                    {mesasDisponibles.map((mesa) => (
                      <option key={mesa.id} value={mesa.id}>
                        Mesa {mesa.numero} - {mesa.estado}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">Cliente (opcional)</label>
                  <select className="form-select" name="clienteId" value={form.clienteId} onChange={handleFormChange}>
                    <option value="">Sin cliente</option>
                    {clientes.map((cliente) => (
                      <option key={cliente.id} value={cliente.id}>
                        {cliente.nombres} {cliente.apellidos}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="border rounded p-3 mb-3">
                  <div className="row g-2 align-items-end">
                    <div className="col-7">
                      <label className="form-label">Plato</label>
                      <select className="form-select" name="platoId" value={nuevoItem.platoId} onChange={handleItemChange}>
                        {platos.map((plato) => (
                          <option key={plato.id} value={plato.id}>
                            {plato.nombre}
                          </option>
                        ))}
                      </select>
                    </div>
                    <div className="col-3">
                      <label className="form-label">Cantidad</label>
                      <input
                        type="number"
                        min={1}
                        className="form-control"
                        name="cantidad"
                        value={nuevoItem.cantidad}
                        onChange={handleItemChange}
                      />
                    </div>
                    <div className="col-2 d-grid">
                      <button type="button" className="btn btn-outline-primary" onClick={agregarItem}>
                        +
                      </button>
                    </div>
                  </div>
                  {items.length > 0 && (
                    <ul className="list-group list-group-flush mt-3">
                      {items.map((item, index) => {
                        const plato = platos.find((p) => p.id === item.platoId)
                        return (
                          <li key={`${item.platoId}-${index}`} className="list-group-item d-flex justify-content-between align-items-center">
                            <span>
                              {plato?.nombre} x {item.cantidad}
                            </span>
                            <button type="button" className="btn btn-sm btn-outline-danger" onClick={() => eliminarItem(index)}>
                              Quitar
                            </button>
                          </li>
                        )
                      })}
                    </ul>
                  )}
                </div>
                <button className="btn btn-primary w-100">Registrar pedido</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default PedidosPage

