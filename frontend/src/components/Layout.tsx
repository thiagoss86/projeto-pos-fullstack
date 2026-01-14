import { Link, NavLink, Outlet, useNavigate } from 'react-router-dom'
import { clearToken, useAuth } from '../lib/auth'
import { logout } from '../lib/api'

function navClass({ isActive }: { isActive: boolean }) {
  return [
    'px-3 py-2 rounded-lg text-sm font-medium',
    isActive ? 'bg-slate-900 text-white' : 'text-slate-700 hover:bg-slate-200'
  ].join(' ')
}

export default function Layout() {
  const navigate = useNavigate()
  const { token } = useAuth()

  const onLogout = async () => {
    try {
      if (token) await logout(token)
    } catch {
      // Se falhar, ainda assim limpamos o token local
    } finally {
      clearToken()
      navigate('/login')
    }
  }

  return (
    <div className="min-h-screen">
      <header className="border-b bg-white">
        <div className="mx-auto max-w-6xl px-4 py-3 flex items-center justify-between">
          <Link to="/" className="font-semibold tracking-tight">
            Cars
          </Link>

          <nav className="flex items-center gap-2">
            {token && (
              <>
                <NavLink to="/cars" className={navClass}>
                  Carros
                </NavLink>
                <NavLink to="/profile" className={navClass}>
                  Meu perfil
                </NavLink>
                <button
                  onClick={onLogout}
                  className="px-3 py-2 rounded-lg text-sm font-medium text-slate-700 hover:bg-slate-200"
                >
                  Sair
                </button>
              </>
            )}
          </nav>
        </div>
      </header>

      <main className="mx-auto max-w-6xl px-4 py-6">
        <Outlet />
      </main>
    </div>
  )
}
