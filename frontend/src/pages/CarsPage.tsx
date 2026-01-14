import { FormEvent, useEffect, useMemo, useState } from 'react'
import Alert from '../components/Alert'
import Modal from '../components/Modal'
import { Button, Input, Label } from '../components/Field'
import { ApiError, Car, createCar, deleteCar, listCars, searchCars, updateCar } from '../lib/api'

const emptyCar: Car = {
  modelo: '',
  ano: new Date().getFullYear(),
  cor: '',
  cavalosDePotencia: 0,
  fabricante: '',
  pais: ''
}

function validate(car: Car): string[] {
  const errs: string[] = []
  if (!car.modelo?.trim()) errs.push('Modelo é obrigatório')
  if (!car.fabricante?.trim()) errs.push('Fabricante é obrigatório')
  if (!car.pais?.trim()) errs.push('País é obrigatório')
  if (!Number.isFinite(car.ano) || car.ano < 1886 || car.ano > new Date().getFullYear() + 1)
    errs.push('Ano inválido')
  if (!Number.isFinite(car.cavalosDePotencia) || car.cavalosDePotencia < 0) errs.push('Cavalos de potência inválido')
  if (!car.cor?.trim()) errs.push('Cor é obrigatória')
  return errs
}

export default function CarsPage() {
  const [cars, setCars] = useState<Car[]>([])
  const [total, setTotal] = useState(0)
  const [page, setPage] = useState(0)
  const [size, setSize] = useState(10)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const [search, setSearch] = useState({ modelo: '', fabricante: '', pais: '' })
  const [searchMode, setSearchMode] = useState(false)

  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState<Car | null>(null)
  const [form, setForm] = useState<Car>(emptyCar)
  const [formErrors, setFormErrors] = useState<string[]>([])
  const [saving, setSaving] = useState(false)

  const totalPages = useMemo(() => Math.max(1, Math.ceil(total / size)), [total, size])

  const load = async () => {
    setError(null)
    setLoading(true)
    try {
      const res = await listCars(page, size)
      setCars(res.cars)
      setTotal(res.total)
      setSearchMode(false)
    } catch (e: any) {
      setError(e?.message ?? 'Falha ao carregar carros')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    void load()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page, size])

  const onSearch = async (e: FormEvent) => {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      const res = await searchCars({
        modelo: search.modelo || undefined,
        fabricante: search.fabricante || undefined,
        pais: search.pais || undefined
      })
      setCars(res)
      setTotal(res.length)
      setSearchMode(true)
    } catch (e: any) {
      setError(e?.message ?? 'Falha ao pesquisar')
    } finally {
      setLoading(false)
    }
  }

  const openCreate = () => {
    setEditing(null)
    setForm(emptyCar)
    setFormErrors([])
    setOpen(true)
  }

  const openEdit = (car: Car) => {
    setEditing(car)
    setForm({
      modelo: car.modelo,
      ano: car.ano,
      cor: car.cor,
      cavalosDePotencia: car.cavalosDePotencia,
      fabricante: car.fabricante,
      pais: car.pais
    })
    setFormErrors([])
    setOpen(true)
  }

  const onSave = async () => {
    const errs = validate(form)
    setFormErrors(errs)
    if (errs.length) return

    setSaving(true)
    setError(null)
    try {
      if (editing?.id) {
        const updated = await updateCar(editing.id, form)
        setCars((prev) => prev.map((c) => (c.id === editing.id ? updated : c)))
      } else {
        const created = await createCar(form)
        setCars((prev) => [created, ...prev])
        setTotal((t) => t + 1)
      }
      setOpen(false)
    } catch (e: any) {
      const msg = e instanceof ApiError ? e.message : (e?.message ?? 'Falha ao salvar')
      setError(msg)
    } finally {
      setSaving(false)
    }
  }

  const onDelete = async (car: Car) => {
    if (!car.id) return
    const ok = confirm(`Excluir "${car.modelo}"?`)
    if (!ok) return
    setError(null)
    try {
      await deleteCar(car.id)
      setCars((prev) => prev.filter((c) => c.id !== car.id))
      setTotal((t) => Math.max(0, t - 1))
    } catch (e: any) {
      setError(e?.message ?? 'Falha ao excluir')
    }
  }

  return (
    <div className="space-y-4">
      <div className="flex items-start justify-between gap-4">
        <div>
          <h1 className="text-xl font-semibold">Carros</h1>
        </div>
        <Button onClick={openCreate}>Novo carro</Button>
      </div>

      {error ? <Alert title="Ocorreu um erro" message={error} /> : null}

      <form onSubmit={onSearch} className="rounded-2xl border bg-white p-4 shadow-sm">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-3 items-end">
          <div>
            <Label>Modelo</Label>
            <Input value={search.modelo} onChange={(e) => setSearch((s) => ({ ...s, modelo: e.target.value }))} />
          </div>
          <div>
            <Label>Fabricante</Label>
            <Input value={search.fabricante} onChange={(e) => setSearch((s) => ({ ...s, fabricante: e.target.value }))} />
          </div>
          <div>
            <Label>País</Label>
            <Input value={search.pais} onChange={(e) => setSearch((s) => ({ ...s, pais: e.target.value }))} />
          </div>
          <div className="flex gap-2">
            <Button type="submit" disabled={loading}>Pesquisar</Button>
            <Button type="button" variant="secondary" onClick={() => { setSearch({ modelo: '', fabricante: '', pais: '' }); void load() }} disabled={loading}>
              Limpar
            </Button>
          </div>
        </div>
      </form>

      <div className="rounded-2xl border bg-white shadow-sm overflow-hidden">
        <div className="overflow-auto">
          <table className="min-w-full text-sm">
            <thead className="bg-slate-100 text-slate-700">
              <tr>
                <th className="text-left px-4 py-3">Modelo</th>
                <th className="text-left px-4 py-3">Fabricante</th>
                <th className="text-left px-4 py-3">País</th>
                <th className="text-left px-4 py-3">Ano</th>
                <th className="text-left px-4 py-3">Cor</th>
                <th className="text-left px-4 py-3">HP</th>
                <th className="text-right px-4 py-3 w-44">Ações</th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr><td colSpan={7} className="px-4 py-6 text-slate-600">Carregando…</td></tr>
              ) : cars.length === 0 ? (
                <tr><td colSpan={7} className="px-4 py-6 text-slate-600">Nenhum registro.</td></tr>
              ) : (
                cars.map((c) => (
                  <tr key={c.id ?? `${c.modelo}-${c.fabricante}-${c.ano}`} className="border-t">
                    <td className="px-4 py-3 font-medium">{c.modelo}</td>
                    <td className="px-4 py-3">{c.fabricante}</td>
                    <td className="px-4 py-3">{c.pais}</td>
                    <td className="px-4 py-3">{c.ano}</td>
                    <td className="px-4 py-3">{c.cor}</td>
                    <td className="px-4 py-3">{c.cavalosDePotencia}</td>
                    <td className="px-4 py-3">
                      <div className="flex justify-end gap-2">
                        <Button variant="secondary" onClick={() => openEdit(c)}>Editar</Button>
                        <Button variant="danger" onClick={() => onDelete(c)}>Excluir</Button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        <div className="border-t bg-white px-4 py-3 flex items-center justify-between">
          <div className="text-sm text-slate-600">
            {searchMode ? (
              <>Exibindo {cars.length} resultados (modo pesquisa).</>
            ) : (
              <>Total: {total} — Página {page + 1} de {totalPages}</>
            )}
          </div>
          {!searchMode && (
            <div className="flex items-center gap-2">
              <select
                className="rounded-xl border px-2 py-2 text-sm"
                value={size}
                onChange={(e) => { setPage(0); setSize(Number(e.target.value)) }}
              >
                {[5, 10, 20, 50].map((n) => (
                  <option key={n} value={n}>{n}/página</option>
                ))}
              </select>

              <Button variant="secondary" onClick={() => setPage((p) => Math.max(0, p - 1))} disabled={page === 0}>
                Anterior
              </Button>
              <Button
                variant="secondary"
                onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
                disabled={page >= totalPages - 1}
              >
                Próxima
              </Button>
            </div>
          )}
        </div>
      </div>

      <Modal
        open={open}
        title={editing ? 'Editar carro' : 'Novo carro'}
        onClose={() => (saving ? null : setOpen(false))}
      >
        {formErrors.length ? (
          <div className="mb-4">
            <Alert title="Verifique os campos" message={formErrors.join(' • ')} />
          </div>
        ) : null}

        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
          <div>
            <Label>Modelo</Label>
            <Input value={form.modelo} onChange={(e) => setForm((f) => ({ ...f, modelo: e.target.value }))} />
          </div>
          <div>
            <Label>Fabricante</Label>
            <Input value={form.fabricante} onChange={(e) => setForm((f) => ({ ...f, fabricante: e.target.value }))} />
          </div>
          <div>
            <Label>País</Label>
            <Input value={form.pais} onChange={(e) => setForm((f) => ({ ...f, pais: e.target.value }))} />
          </div>
          <div>
            <Label>Ano</Label>
            <Input
              type="number"
              value={form.ano}
              onChange={(e) => setForm((f) => ({ ...f, ano: Number(e.target.value) }))}
            />
          </div>
          <div>
            <Label>Cor</Label>
            <Input value={form.cor} onChange={(e) => setForm((f) => ({ ...f, cor: e.target.value }))} />
          </div>
          <div>
            <Label>Cavalos de potência</Label>
            <Input
              type="number"
              value={form.cavalosDePotencia}
              onChange={(e) => setForm((f) => ({ ...f, cavalosDePotencia: Number(e.target.value) }))}
            />
          </div>
        </div>

        <div className="mt-5 flex justify-end gap-2">
          <Button variant="secondary" onClick={() => setOpen(false)} disabled={saving}>Cancelar</Button>
          <Button onClick={onSave} disabled={saving}>{saving ? 'Salvando…' : 'Salvar'}</Button>
        </div>
      </Modal>
    </div>
  )
}
