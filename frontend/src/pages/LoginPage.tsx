import { FormEvent, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Alert from '../components/Alert'
import { Button, Input, Label } from '../components/Field'
import { login } from '../lib/api'
import { setToken } from '../lib/auth'

export default function LoginPage() {
  const navigate = useNavigate()
  const [email, setEmail] = useState('wile@acme.com')
  const [password, setPassword] = useState('senha123')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const onSubmit = async (e: FormEvent) => {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      const res = await login(email, password)
      setToken(`Bearer ${res.token}`)
      navigate('/cars')
    } catch (err: any) {
      setError(err?.message ?? 'Falha ao autenticar')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="mx-auto max-w-md">
      <div className="rounded-2xl border bg-white shadow-sm p-6">
        <h1 className="text-xl font-semibold">Entrar</h1>

        {error ? <div className="mt-4"><Alert title="Não foi possível entrar" message={error} /></div> : null}

        <form onSubmit={onSubmit} className="mt-4 space-y-4">
          <div>
            <Label>e-mail</Label>
            <Input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="seu@email.com" />
          </div>

          <div>
            <Label>senha</Label>
            <Input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
            />
          </div>

          <Button type="submit" disabled={loading} className="w-full">
            {loading ? 'Entrando…' : 'Entrar'}
          </Button>

        </form>
      </div>
    </div>
  )
}
