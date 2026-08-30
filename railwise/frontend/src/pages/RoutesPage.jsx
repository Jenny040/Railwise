import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

export default function RoutesPage() {
  const [routes, setRoutes] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [form, setForm] = useState({
    routeName: '',
    originStation: '',
    destinationStation: '',
    distanceKm: '',
    operator: '',
  })

  useEffect(() => {
    loadRoutes()
  }, [])

  async function loadRoutes() {
    try {
      setLoading(true)
      setRoutes(await api.getRoutes())
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      await api.createRoute({
        ...form,
        distanceKm: form.distanceKm ? Number(form.distanceKm) : null,
      })
      setForm({ routeName: '', originStation: '', destinationStation: '', distanceKm: '', operator: '' })
      loadRoutes()
    } catch (err) {
      setError(err.message)
    }
  }

  async function handleDelete(id) {
    await api.deleteRoute(id)
    loadRoutes()
  }

  return (
    <section>
      <h2>Routes</h2>

      <form onSubmit={handleSubmit} className="card">
        <input
          placeholder="Route name (e.g. Cape Flats Line)"
          value={form.routeName}
          onChange={(e) => setForm({ ...form, routeName: e.target.value })}
          required
        />
        <input
          placeholder="Origin station"
          value={form.originStation}
          onChange={(e) => setForm({ ...form, originStation: e.target.value })}
          required
        />
        <input
          placeholder="Destination station"
          value={form.destinationStation}
          onChange={(e) => setForm({ ...form, destinationStation: e.target.value })}
          required
        />
        <input
          placeholder="Distance (km)"
          type="number"
          value={form.distanceKm}
          onChange={(e) => setForm({ ...form, distanceKm: e.target.value })}
        />
        <input
          placeholder="Operator (e.g. Metrorail)"
          value={form.operator}
          onChange={(e) => setForm({ ...form, operator: e.target.value })}
        />
        <button type="submit">Add route</button>
      </form>

      {error && <p className="error">{error}</p>}
      {loading ? (
        <p>Loading...</p>
      ) : (
        <ul className="list">
          {routes.map((r) => (
            <li key={r.id} className="card">
              <strong>{r.routeName}</strong> — {r.originStation} → {r.destinationStation}
              {r.distanceKm ? ` (${r.distanceKm} km)` : ''} {r.operator ? `· ${r.operator}` : ''}
              <button onClick={() => handleDelete(r.id)}>Delete</button>
            </li>
          ))}
        </ul>
      )}
    </section>
  )
}
