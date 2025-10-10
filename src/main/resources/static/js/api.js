const API_BASE = '/api/v1/movies';

export async function fetchTopRated() {
  const res = await fetch(`${API_BASE}/top-rated`);
  if (!res.ok) throw new Error(`Failed to load top movies: ${res.status} ${res.statusText}`);
  return res.json();
}

export async function searchByTitle(q) {
  if (!q) return [];
  const res = await fetch(`${API_BASE}/search?title=${encodeURIComponent(q)}`);
  if (!res.ok) throw new Error(`Search failed: ${res.status}`);
  return res.json();
}
