// Central place all backend calls go through. Change the base URL
// in lib/config.js when you deploy the backend, not here.
import { API_BASE_URL } from './config.js'

async function request(path, options = {}) {
  const res = await fetch(`${API_BASE_URL}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  })

  if (!res.ok) {
    const body = await res.json().catch(() => ({}))
    throw new Error(body.message || `Request failed: ${res.status}`)
  }

  if (res.status === 204) return null
  return res.json()
}

export const api = {
  // Routes
  getRoutes: () => request('/api/v1/routes'),
  getRoute: (id) => request(`/api/v1/routes/${id}`),
  createRoute: (data) => request('/api/v1/routes', { method: 'POST', body: JSON.stringify(data) }),
  deleteRoute: (id) => request(`/api/v1/routes/${id}`, { method: 'DELETE' }),

  // Journeys
  getJourneys: (routeId) => request(`/api/v1/journeys${routeId ? `?routeId=${routeId}` : ''}`),
  getJourney: (id) => request(`/api/v1/journeys/${id}`),
  createJourney: (data) => request('/api/v1/journeys', { method: 'POST', body: JSON.stringify(data) }),
  updateJourneyStatus: (id, data) =>
    request(`/api/v1/journeys/${id}/status`, { method: 'PATCH', body: JSON.stringify(data) }),
  deleteJourney: (id) => request(`/api/v1/journeys/${id}`, { method: 'DELETE' }),
}
