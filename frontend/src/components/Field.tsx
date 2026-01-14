import { InputHTMLAttributes } from 'react'

export function Label({ children }: { children: string }) {
  return <div className="text-sm font-medium text-slate-700 mb-1">{children}</div>
}

export function Input(props: InputHTMLAttributes<HTMLInputElement>) {
  return (
    <input
      {...props}
      className={[
        'w-full rounded-xl border px-3 py-2 text-sm outline-none',
        'focus:ring-2 focus:ring-slate-300',
        props.className ?? ''
      ].join(' ')}
    />
  )
}

export function Button({
  children,
  variant = 'primary',
  ...props
}: React.ButtonHTMLAttributes<HTMLButtonElement> & { variant?: 'primary' | 'secondary' | 'danger' }) {
  const cls =
    variant === 'primary'
      ? 'bg-slate-900 text-white hover:bg-slate-800'
      : variant === 'danger'
        ? 'bg-red-600 text-white hover:bg-red-500'
        : 'bg-slate-200 text-slate-900 hover:bg-slate-300'

  return (
    <button
      {...props}
      className={[
        'inline-flex items-center justify-center rounded-xl px-4 py-2 text-sm font-medium disabled:opacity-50 disabled:cursor-not-allowed',
        cls,
        props.className ?? ''
      ].join(' ')}
    >
      {children}
    </button>
  )
}
