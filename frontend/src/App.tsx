import { Navigate, Route, Routes } from 'react-router-dom'
import Layout from './components/Layout'
import LoginPage from './pages/LoginPage'
import CarsPage from './pages/CarsPage'
import ProfilePage from './pages/ProfilePage'
import { useAuth } from './lib/auth'

export default function App() {
  const { token } = useAuth()

  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route element={<Layout />}>
        <Route
          path="/"
          element={token ? <Navigate to="/cars" replace /> : <Navigate to="/login" replace />}
        />
        <Route
          path="/cars"
          element={token ? <CarsPage /> : <Navigate to="/login" replace />}
        />
        <Route
          path="/profile"
          element={token ? <ProfilePage /> : <Navigate to="/login" replace />}
        />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}
