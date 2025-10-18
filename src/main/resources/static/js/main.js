import { fetchTopRated, searchByTitle } from './api.js';

const moviesRow = document.getElementById('moviesRow');
const loading = document.getElementById('loading');
const error = document.getElementById('error');
const searchBox = document.getElementById('searchBox');
const movieModal = new bootstrap.Modal(document.getElementById('movieDetailsModal'));
const modalContent = document.getElementById('movieDetailsContent');

async function init() {
  showLoading();
  try {
    const movies = await fetchTopRated();
    renderMovies(movies);
  } catch (e) {
    showError(e);
  } finally {
    hideLoading();
  }
}

function renderMovies(movies) {
  if (!movies || movies.length === 0) {
    moviesRow.innerHTML = `<div class="col-12"><p>No movies found.</p></div>`;
    return;
  }

  moviesRow.innerHTML = movies.map(movieCardHtml).join('');
  document.querySelectorAll('.movie-card').forEach(card => {
    card.addEventListener('click', () => showMovieDetails(card.dataset.id));
  });
}

function movieCardHtml(m) {
  const id = m.id ?? '';
  const title = escapeHtml(m.title ?? 'Untitled');
  const year = m.releaseYear ?? '';
  const rating = m.imdbRating ?? '';
  const votes = numberWithCommas(m.numberOfVotes) ?? '';
  const poster = m.posterLink ?? 'img/placeholder.png';

  return `
    <div class="col-sm-6 col-md-4 col-lg-3">
      <div class="card movie-card h-100" data-id="${id}" style="cursor:pointer">
        <div style="height:360px; overflow:hidden;">
          <img src="${escapeHtml(poster)}" class="card-img-top" alt="${title}" style="width:100%; height:100%; object-fit:cover;">
        </div>
        <div class="card-body">
          <h5 class="card-title mb-1">${title}</h5>
          <p class="card-text small text-muted mb-0">${year ? `Year: ${year}` : ''} <i class="bi bi-film"></i></p>
          <p class="card-text small text-muted mb-1">Rating: ${rating} <i class="bi bi-star-fill"></i></p>
          <p class="card-text small text-muted mb-1">Votes: ${votes} <i class="bi bi-hand-thumbs-up-fill"></i></p>
        </div>
      </div>
    </div>`;
}

async function showMovieDetails(id) {
  modalContent.innerHTML = '<p class="text-center">Loading...</p>';
  movieModal.show();

  try {
    const res = await fetch(`/api/v1/movies/${id}`);
    if (!res.ok) throw new Error('Movie not found');
    const movie = await res.json();
    const poster = movie.posterLink ?? 'img/placeholder.png';

    modalContent.innerHTML = `
      <div class="row">
        <div class="col-md-4">
          <img src="${poster}" class="img-fluid" alt="${movie.title}">
        </div>
        <div class="col-md-8">
          <h3>${movie.title} (${movie.releaseYear})</h3>
          ${movie.originalTitle ? `<p><strong>Original Title:</strong> ${escapeHtml(movie.originalTitle)}</p>` : ''}
          <p><strong>Genres:</strong> ${movie.genres.join(', ')}</p>
          <p><strong>Certificate:</strong> ${movie.certificate}</p>
          <p><strong>Run time:</strong> ${movie.runTime}</p>
          <p><strong>Overview:</strong> ${movie.overview}</p>
          <p><strong>IMDB Rating:</strong> ${movie.imdbRating}</p>
          <p><strong>MetaScore:</strong> ${movie.metaScore}</p>
          <p><strong>Votes:</strong> ${numberWithCommas(movie.numberOfVotes)}</p>
          <p><strong>Director:</strong> ${movie.director}</p>
          <p><strong>Cast:</strong> ${movie.cast.join(', ')}</p>
          <p><strong>Gross: $</strong> ${movie.gross}</p>
        </div>
      </div>`;
  } catch (e) {
    modalContent.innerHTML = `<div class="alert alert-danger">Failed to load movie details: ${e.message}</div>`;
  }
}

function numberWithCommas(number) {
  return number?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") ?? '';
}

function escapeHtml(str) {
  if (!str) return '';
  return String(str).replace(/[&<>"']/g, s => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' })[s]);
}

function showLoading() {
  loading.style.display = 'block';
  error.classList.add('d-none');
}

function hideLoading() {
  loading.style.display = 'none';
}

// Search
let searchTimeout;

searchBox.addEventListener('input', (ev) => {
  const q = ev.target.value.trim();
  clearTimeout(searchTimeout);
  suggestions.innerHTML = '';

  if (q.length < 2) {
    suggestions.style.display = 'none';
    return;
  }

  searchTimeout = setTimeout(async () => {
    try {
      const results = await searchByTitle(q);
      showSuggestions(results);
    } catch (e) {
      console.error(e);
    }
  }, 300);
});

function showSuggestions(movies) {
  if (!movies || movies.length === 0) {
    suggestions.innerHTML = `<li class="list-group-item list-group-item-action">
     No movies found!
    </li>`
    return;
  }

  suggestions.innerHTML = movies.map(m => `
    <li class="list-group-item list-group-item-action" data-id="${m.id}">
      ${escapeHtml(m.title)}
    </li>
  `).join('');

  suggestions.style.display = 'block';

  document.querySelectorAll('#suggestions li').forEach(li => {
    li.addEventListener('click', () => {
      const id = li.dataset.id;
      searchBox.value = li.textContent;
      suggestions.style.display = 'none';
      showMovieDetails(id);
    });
  });
};

function showError(e) {
  console.error(e);
  error.classList.remove('d-none');
  error.textContent = 'Failed to load movies. ' + (e.message || e);
}

// Initialize
init();
