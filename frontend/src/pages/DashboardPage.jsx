import { useEffect, useState } from 'react'
import api from '../api/client.js'

const cards = [
  { key: 'clientes', label: 'Clientes', color: 'primary' },
  { key: 'mesasDisponibles', label: 'Mesas disponibles', color: 'success' },
  { key: 'pedidosPendientes', label: 'Pedidos pendientes', color: 'warning' },
  { key: 'ventas', label: 'Ventas registradas', color: 'info' },
  { key: 'alertas', label: 'Alertas de inventario', color: 'danger' },
]

const DashboardPage = () => {
  const [summary, setSummary] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const fetchSummary = async () => {
      try {
        const { data } = await api.get('/api/dashboard/summary')
        setSummary(data)
      } finally {
        setLoading(false)
      }
    }
    fetchSummary()
  }, [])

  if (loading || !summary) {
    return <div className="spinner-border text-primary" role="status" />
  }

  return (
    <section>
      <h2 className="mb-4">Panel general</h2>
      <div className="row g-3">
        {cards.map((card) => (
          <div className="col-md-4" key={card.key}>
            <div className={`card border-${card.color}`}>
              <div className={`card-body text-${card.color}`}>
                <p className="mb-1 text-uppercase small fw-semibold">{card.label}</p>
                <h3 className="mb-0">{summary[card.key]}</h3>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  )
}

export default DashboardPage

