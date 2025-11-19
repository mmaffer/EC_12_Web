import { Outlet } from 'react-router-dom'
import Sidebar from '../components/Sidebar.jsx'
import { useAuth } from '../hooks/useAuth.js'

const AppLayout = () => {
  const { user, logout } = useAuth()

  return (
    <div className="app-container d-flex min-vh-100">
      <Sidebar />
      <div className="flex-grow-1 bg-light">
        <header className="d-flex justify-content-between align-items-center p-3 border-bottom bg-white">
          <div>
            <h5 className="mb-0">Bienvenido, {user?.nombreUsuario}</h5>
            <small className="text-muted text-uppercase">{user?.rol}</small>
          </div>
          <button className="btn btn-outline-danger btn-sm" onClick={logout}>
            Cerrar sesión
          </button>
        </header>
        <main className="p-4">
          <Outlet />
        </main>
      </div>
    </div>
  )
}

export default AppLayout

