const API_BASE = '/api/v1/movies';

export async function fetchTopRated() {
    const res = await fetch(`${API_BASE}/top-rated`);
    if (!res.ok) throw new Error(`Failed to load top movies: ${res.status} ${res.statusText}`);
    return res.json();
}

export async function fetchAllMovies(page = 0, sort = "imdbRating,desc") {
    const response = await fetch(`${API_BASE}/all?page=${page}&size=50&sort=${sort}`);
    if (!response.ok) throw new Error("Failed to load movies");
    return response.json();
}

export async function searchByTitle(prefix) {
    const res = await fetch(`${API_BASE}/search?titlePrefix=${encodeURIComponent(prefix)}`);
    if (!res.ok) throw new Error('Failed to search movies');
    return res.json();
}
