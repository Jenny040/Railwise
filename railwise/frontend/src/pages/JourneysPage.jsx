import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

export default function JourneysPage() {
  const [journeys, setJourneys] = useState([])
  const [routes, setRoutes] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [form, setForm] = useState({
    routeId: '',
    scheduledDeparture: '',
    scheduledArrival: '',
    notes: '',
  })

  useEffect(() => {
    loadData()
  }, [])

  async function loadData() {
    try {
      setLoading(true)
      const [journeysData, routesData] = await Promise.all([api.getJourneys(), api.getRoutes()])
      setJourneys(journeysData)
      setRoutes(routesData)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      await api.createJourney({
        routeId: Number(form.routeId),
        scheduledDeparture: new Date(form.scheduledDeparture).toISOString(),
        scheduledArrival: form.scheduledArrival ? new Date(form.scheduledArrival).toISOString() : null,
        notes: form.notes,
      })
      setForm({ routeId: '', scheduledDeparture: '', scheduledArrival: '', notes: '' })
      loadData()
    } catch (err) {
      setError(err.message)
    }
  }

  async function markStatus(id, status) {
    await api.updateJourneyStatus(id, { status })
    loadData()
  }

  return (
    <section>
      <h2>Journeys</h2>

      <form onSubmit={handleSubmit} className="card">
        <select
          value={form.routeId}
          onChange={(e) => setForm({ ...form, routeId: e.target.value })}
          required
        >
          <option value="">Select a route...</option>
          {routes.map((r) => (
            <option key={r.id} value={r.id}>
              {r.routeName} ({r.originStation} → {r.destinationStation})
            </option>
          ))}
        </select>
        <input
          type="datetime-local"
          value={form.scheduledDeparture}
          onChange={(e) => setForm({ ...form, scheduledDeparture: e.target.value })}
          required
        />
        <input
          type="datetime-local"
          value={form.scheduledArrival}
          onChange={(e) => setForm({ ...form, scheduledArrival: e.target.value })}
        />
        <input
          placeholder="Notes"
          value={form.notes}
          onChange={(e) => setForm({ ...form, notes: e.target.value })}
        />
        <button type="submit">Log journey</button>
      </form>

      {error && <p className="error">{error}</p>}
      {loading ? (
        <p>Loading...</p>
      ) : (
        <ul className="list">
          {journeys.map((j) => (
            <li key={j.id} className="card">
              <strong>Route #{j.routeId}</strong> — {new Date(j.scheduledDeparture).toLocaleString()}
              <div>Status: {j.status}</div>
              <div className="actions">
                <button onClick={() => markStatus(j.id, 'DELAYED')}>Mark delayed</button>
                <button onClick={() => markStatus(j.id, 'COMPLETED')}>Mark completed</button>
                <button onClick={() => markStatus(j.id, 'CANCELLED')}>Cancel</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </section>
  )
}
