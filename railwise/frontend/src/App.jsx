import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom'
import RoutesPage from './pages/RoutesPage.jsx'
import JourneysPage from './pages/JourneysPage.jsx'
import './App.css'

export default function App() {
  return (
    <BrowserRouter>
      <div className="app-shell">
        <header className="app-header">
          <h1>Railwise</h1>
          <nav>
            <NavLink to="/" end>Routes</NavLink>
            <NavLink to="/journeys">Journeys</NavLink>
          </nav>
        </header>

        <main className="app-main">
          <Routes>
            <Route path="/" element={<RoutesPage />} />
            <Route path="/journeys" element={<JourneysPage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  )
}
