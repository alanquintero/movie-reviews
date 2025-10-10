import { fetchTopRated, searchByTitle } from './api.js';

const moviesRow = document.getElementById('moviesRow');
const loading = document.getElementById('loading');
const error = document.getElementById('error');
const searchBox = document.getElementById('searchBox');

async function init() {
  try {
    const movies = await fetchTopRated();
    renderMovies(movies);
  } catch (e) {
    showError(e);
  } finally {
    loading.style.display = 'none';
  }
}

function renderMovies(movies) {
  if (!movies || movies.length === 0) {
    moviesRow.innerHTML = `<div class="col-12"><p>No movies found.</p></div>`;
    return;
  }

  moviesRow.innerHTML = movies.map(m => movieCardHtml(m)).join('');
  // add click handlers to cards
  document.querySelectorAll('.movie-card').forEach(card => {
    card.addEventListener('click', () => {
      const id = card.dataset.id;
      // TODO go to movie details page
      window.location.href = `/movie.html?id=${encodeURIComponent(id)}`;
    });
  });
}

function movieCardHtml(m) {
  // tolerant access (works with different DTO field names)
  const id = m.id ?? '';
  const title = escapeHtml(m.title ?? 'Untitled');
  const year = m.releaseYear ?? '';
  const rating = m.imdbRating ?? '';
  const votes = numberWithCommas(m.numberOfVotes) ?? '';
  const poster = m.posterLink ?? m.thumbnail ?? '/img/placeholder.png';
  return `
    <div class="col-sm-6 col-md-4 col-lg-3">
      <div class="card movie-card h-100" data-id="${id}" style="cursor:pointer">
        <div style="height:360px; overflow:hidden;">
          <img src="${escapeHtml(poster)}" class="card-img-top" alt="${title}" style="width:100%; height:100%; object-fit:cover;">
        </div>
        <div class="card-body">
          <h5 class="card-title mb-1">${title}</h5>
          <p class="card-text small text-muted mb-0">${year ? `Year: ${year}` : ''}</p>
          <p class="card-text small text-muted">Rating: ${rating} | Votes: ${votes}</p>
        </div>
      </div>
    </div>`;
}

function numberWithCommas(number) {
    return number.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}

function escapeHtml(str) {
  if (!str) return '';
  return String(str).replace(/[&<>"']/g, s => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' })[s]);
}

function showError(e) {
  console.error(e);
  error.classList.remove('d-none');
  error.textContent = 'Failed to load movies. ' + (e.message || e);
}

// TODO complete the search functionality
let searchTimeout;
searchBox.addEventListener('input', (ev) => {
  const q = ev.target.value.trim();
  clearTimeout(searchTimeout);
  if (q.length < 2) {
    // reload top movies when query is short
    searchTimeout = setTimeout(init, 300);
    return;
  }
  searchTimeout = setTimeout(async () => {
    try {
      loading.style.display = 'block';
      const results = await searchByTitle(q);
      renderMovies(results);
    } catch (e) {
      showError(e);
    } finally {
      loading.style.display = 'none';
    }
  }, 350);
});

// kick it off
init();
