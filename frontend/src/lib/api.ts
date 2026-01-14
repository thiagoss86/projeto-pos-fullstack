export type Car = {
  id?: number
  modelo: string
  ano: number
  cor: string
  cavalosDePotencia: number
  fabricante: string
  pais: string
}

export type Usuario = {
  id: number
  name?: string
  email: string
}

const API_BASE = import.meta.env.VITE_API_URL ?? '/api'

function getAuthHeader(): Record<string, string> {
  const token = localStorage.getItem('cars.token')
  return token ? { Authorization: token } : {}
}

export class ApiError extends Error {
  status: number
  details?: unknown
  constructor(message: string, status: number, details?: unknown) {
    super(message)
    this.status = status
    this.details = details
  }
}

async function request<T>(path: string, opts: RequestInit = {}): Promise<T> {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: {
      'content-type': 'application/json',
      ...getAuthHeader(),
      ...(opts.headers ?? {})
    },
    ...opts
  })

  const contentType = res.headers.get('content-type') || ''
  const isJson = contentType.includes('application/json')
  const body = isJson ? await res.json().catch(() => undefined) : await res.text().catch(() => undefined)

  if (!res.ok) {
    const msg =
      (body && typeof body === 'object' && 'message' in body && typeof (body as any).message === 'string')
        ? (body as any).message
        : `Erro HTTP ${res.status}`
    throw new ApiError(msg, res.status, body)
  }

  return body as T
}

export async function login(email: string, password: string): Promise<{ token: string }> {
  return request('/usuarios/login', {
    method: 'POST',
    body: JSON.stringify({ email, password })
  })
}

export async function myProfile(token: string): Promise<Usuario> {
  return request('/usuarios/my-profile', {
    headers: { Authorization: token }
  })
}

export async function logout(token: string): Promise<void> {
  await request('/usuarios/logout', {
    method: 'POST',
    headers: { Authorization: token }
  })
}

export async function listCars(page: number, size: number): Promise<{ cars: Car[]; total: number }> {
  const res = await fetch(`${API_BASE}/carros?page=${encodeURIComponent(String(page))}&size=${encodeURIComponent(String(size))}`, {
    headers: {
      ...getAuthHeader(),
      'content-type': 'application/json'
    }
  })
  const total = Number(res.headers.get('X-Total-Count') ?? '0')
  if (!res.ok) {
    let msg = `Erro HTTP ${res.status}`
    try {
      const j = await res.json()
      if (j?.message) msg = j.message
    } catch {}
    throw new ApiError(msg, res.status)
  }
  const cars = (await res.json()) as Car[]
  return { cars, total }
}

export async function searchCars(params: { modelo?: string; fabricante?: string; pais?: string }): Promise<Car[]> {
  const usp = new URLSearchParams()
  if (params.modelo) usp.set('modelo', params.modelo)
  if (params.fabricante) usp.set('fabricante', params.fabricante)
  if (params.pais) usp.set('pais', params.pais)
  const qs = usp.toString() ? `?${usp.toString()}` : ''
  return request(`/carros/search${qs}`)
}

export async function createCar(car: Car): Promise<Car> {
  return request('/carros', { method: 'POST', body: JSON.stringify(car) })
}

export async function updateCar(id: number, car: Car): Promise<Car> {
  return request(`/carros/${id}`, { method: 'PUT', body: JSON.stringify(car) })
}

export async function deleteCar(id: number): Promise<void> {
  await request(`/carros/${id}`, { method: 'DELETE' })
}
