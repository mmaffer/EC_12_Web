import { NavLink } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth.js'

const navItems = [
  { to: '/', label: 'Panel', roles: ['ALL'] },
  { to: '/clientes', label: 'Clientes', roles: ['ADMIN', 'MOZO'] },
  { to: '/mesas', label: 'Mesas', roles: ['ADMIN', 'MOZO'] },
  { to: '/menu', label: 'Menú', roles: ['ADMIN', 'COCINERO'] },
  { to: '/pedidos', label: 'Pedidos', roles: ['ADMIN', 'MOZO', 'COCINERO'] },
  { to: '/inventario', label: 'Inventario', roles: ['ADMIN'] },
  { to: '/ventas', label: 'Ventas', roles: ['ADMIN', 'CAJERO'] },
  { to: '/admin/usuarios', label: 'Usuarios', roles: ['ADMIN'] },
  { to: '/admin/bitacora', label: 'Bitácora', roles: ['ADMIN'] },
]

const Sidebar = () => {
  const { user } = useAuth()
  const role = user?.rol ?? 'ALL'

  return (
    <aside className="sidebar bg-dark text-white p-3">
      <div className="mb-4">
        <h4 className="fw-bold">Sabor Gourmet</h4>
        <p className="text-white-50 mb-0">{role}</p>
      </div>
      <nav className="nav flex-column gap-1">
        {navItems
          .filter((item) => item.roles.includes('ALL') || item.roles.includes(role))
          .map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              end={item.to === '/'}
              className={({ isActive }) =>
                `nav-link text-white ${isActive ? 'active bg-primary rounded' : ''}`
              }
            >
              {item.label}
            </NavLink>
          ))}
      </nav>
    </aside>
  )
}

export default Sidebar

