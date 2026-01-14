export default function Alert({ title, message }: { title: string; message?: string }) {
  return (
    <div className="rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-red-900">
      <div className="font-semibold">{title}</div>
      {message ? <div className="text-sm mt-1">{message}</div> : null}
    </div>
  )
}
