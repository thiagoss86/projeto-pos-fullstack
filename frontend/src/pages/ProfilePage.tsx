import { useEffect, useState } from 'react'
import Alert from '../components/Alert'
import { myProfile, Usuario } from '../lib/api'
import { useAuth } from '../lib/auth'

export default function ProfilePage() {
  const { token } = useAuth()
  const [user, setUser] = useState<Usuario | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const run = async () => {
      if (!token) return
      setError(null)
      setLoading(true)
      try {
        const me = await myProfile(token)
        setUser(me)
      } catch (e: any) {
        setError(e?.message ?? 'Falha ao carregar perfil')
      } finally {
        setLoading(false)
      }
    }
    void run()
  }, [token])

  return (
    <div className="max-w-2xl">
      <h1 className="text-xl font-semibold">Meu perfil</h1>

      {error ? <div className="mt-4"><Alert title="Não foi possível carregar" message={error} /></div> : null}

      <div className="mt-4 rounded-2xl border bg-white p-5 shadow-sm">
        {loading ? (
          <div className="text-slate-600">Carregando…</div>
        ) : user ? (
          <div className="space-y-2">
            <div className="text-sm text-slate-600">ID</div>
            <div className="font-medium">{user.id}</div>

            <div className="text-sm text-slate-600 mt-3">E-mail</div>
            <div className="font-medium">{user.email}</div>
          </div>
        ) : (
          <div className="text-slate-600">Sem dados.</div>
        )}
      </div>
    </div>
  )
}
