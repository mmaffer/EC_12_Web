import { Navigate, Route, Routes } from 'react-router-dom'
import ProtectedRoute from './components/ProtectedRoute.jsx'
import AppLayout from './layouts/AppLayout.jsx'
import DashboardPage from './pages/DashboardPage.jsx'
import ClientesPage from './pages/ClientesPage.jsx'
import MesasPage from './pages/MesasPage.jsx'
import MenuPage from './pages/MenuPage.jsx'
import PedidosPage from './pages/PedidosPage.jsx'
import InventarioPage from './pages/InventarioPage.jsx'
import VentasPage from './pages/VentasPage.jsx'
import UsuariosPage from './pages/UsuariosPage.jsx'
import BitacoraPage from './pages/BitacoraPage.jsx'
import LoginPage from './pages/LoginPage.jsx'

const App = () => (
  <Routes>
    <Route path="/login" element={<LoginPage />} />
    <Route
      element={
        <ProtectedRoute>
          <AppLayout />
        </ProtectedRoute>
      }
    >
      <Route path="/" element={<DashboardPage />} />
      <Route path="/clientes" element={<ClientesPage />} />
      <Route path="/mesas" element={<MesasPage />} />
      <Route path="/menu" element={<MenuPage />} />
      <Route path="/pedidos" element={<PedidosPage />} />
      <Route path="/inventario" element={<InventarioPage />} />
      <Route path="/ventas" element={<VentasPage />} />
      <Route path="/admin/usuarios" element={<UsuariosPage />} />
      <Route path="/admin/bitacora" element={<BitacoraPage />} />
    </Route>
    <Route path="*" element={<Navigate to="/" replace />} />
  </Routes>
)

export default App
