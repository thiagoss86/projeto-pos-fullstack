import { useEffect, useState } from 'react'

const STORAGE_KEY = 'cars.token'

export function getToken(): string | null {
  return localStorage.getItem(STORAGE_KEY)
}

export function setToken(token: string) {
  localStorage.setItem(STORAGE_KEY, token)
  window.dispatchEvent(new Event('cars-auth-changed'))
}

export function clearToken() {
  localStorage.removeItem(STORAGE_KEY)
  window.dispatchEvent(new Event('cars-auth-changed'))
}

export function useAuth() {
  const [token, setTok] = useState<string | null>(() => getToken())

  useEffect(() => {
    const onChange = () => setTok(getToken())
    window.addEventListener('cars-auth-changed', onChange)
    return () => window.removeEventListener('cars-auth-changed', onChange)
  }, [])

  return { token, setToken, clearToken }
}
