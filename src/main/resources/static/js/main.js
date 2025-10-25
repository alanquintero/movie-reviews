import {fetchTopRated, searchByTitle, fetchAllMovies} from './api.js';

const moviesRow = document.getElementById('moviesRow');
const loading = document.getElementById('loading');
const error = document.getElementById('error');
const searchBox = document.getElementById('searchBox');
const suggestions = document.getElementById('suggestions');
const movieModal = new bootstrap.Modal(document.getElementById('movieDetailsModal'));
const modalContent = document.getElementById('movieDetailsContent');

const topMoviesSection = document.getElementById('topMoviesSection');
const allMoviesSection = document.getElementById('allMoviesSection');
const topMoviesLink = document.getElementById('topMoviesLink');
const allMoviesLink = document.getElementById('allMoviesLink');
const sortSelect = document.getElementById('sortSelect');
const allMoviesList = document.getElementById('allMoviesList');
const paginationTop = document.getElementById('pagination-top');
const paginationBottom = document.getElementById('pagination-bottom');

let currentPage = 0;
let totalPages = 0;
let currentSort = 'imdbRating,desc';

// ===== INIT =====
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

init();

// ===== TOP RATED MOVIES =====
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

// ===== MOVIE DETAILS MODAL =====
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

// ===== SEARCH =====
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
        suggestions.innerHTML = `<li class="list-group-item list-group-item-action">No movies found!</li>`;
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
}

// ===== ALL MOVIES (Paginated) =====
allMoviesLink.addEventListener('click', async () => {
    switchToSection('all');
    await loadAllMovies(0, currentSort);
});

topMoviesLink.addEventListener('click', () => {
    switchToSection('top');
});

async function loadAllMovies(page = 0, sort = 'imdbRating,desc') {
    showLoading();
    try {
        const data = await fetchAllMovies(page, sort);
        renderAllMovies(data.content);
        totalPages = data.totalPages;
        currentPage = data.number;
        renderPagination();
    } catch (e) {
        showError(e);
    } finally {
        hideLoading();
    }
}

function renderAllMovies(movies) {
    allMoviesList.innerHTML = movies.map(m => `
    <a href="#" class="list-group-item list-group-item-action d-flex align-items-center" data-id="${m.id}">
      <img src="${m.posterLink ?? 'img/placeholder.png'}" alt="${escapeHtml(m.title)}" class="me-3 rounded" style="width:60px; height:90px; object-fit:cover;">
      <div>
        <h6 class="mb-1">${escapeHtml(m.title)}</h6>
        <small class="text-muted">Year: ${m.releaseYear} | Rating: ${m.imdbRating}</small>
      </div>
    </a>
  `).join('');

    allMoviesList.querySelectorAll('a').forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            showMovieDetails(item.dataset.id);
        });
    });
}

function renderPagination() {
    paginationBottom.innerHTML = `
    <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
      <a class="page-link" href="#" id="prevPage">Prev</a>
    </li>
    <li class="page-item disabled">
      <span class="page-link">Page ${currentPage + 1} of ${totalPages}</span>
    </li>
    <li class="page-item ${currentPage + 1 >= totalPages ? 'disabled' : ''}">
      <a class="page-link" href="#" id="nextPage">Next</a>
    </li>
  `;

    paginationBottom.querySelector('#prevPage')?.addEventListener('click', e => {
        e.preventDefault();
        if (currentPage > 0) loadAllMovies(currentPage - 1, currentSort);
    });

    paginationBottom.querySelector('#nextPage')?.addEventListener('click', e => {
        e.preventDefault();
        if (currentPage + 1 < totalPages) loadAllMovies(currentPage + 1, currentSort);
    });

    // Top
    paginationTop.innerHTML = `
      <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
        <a class="page-link" href="#" id="prevPage">Prev</a>
      </li>
      <li class="page-item disabled">
        <span class="page-link">Page ${currentPage + 1} of ${totalPages}</span>
      </li>
      <li class="page-item ${currentPage + 1 >= totalPages ? 'disabled' : ''}">
        <a class="page-link" href="#" id="nextPage">Next</a>
      </li>
    `;

    paginationTop.querySelector('#prevPage')?.addEventListener('click', e => {
        e.preventDefault();
        if (currentPage > 0) loadAllMovies(currentPage - 1, currentSort);
    });

    paginationTop.querySelector('#nextPage')?.addEventListener('click', e => {
        e.preventDefault();
        if (currentPage + 1 < totalPages) loadAllMovies(currentPage + 1, currentSort);
    });
}

sortSelect.addEventListener('change', async () => {
    currentSort = sortSelect.value;
    await loadAllMovies(0, currentSort);
});

// ===== SECTION SWITCH =====
function switchToSection(section) {
    if (section === 'all') {
        allMoviesLink.classList.add('active');
        topMoviesLink.classList.remove('active');
        topMoviesSection.classList.add('d-none');
        allMoviesSection.classList.remove('d-none');
    } else {
        topMoviesLink.classList.add('active');
        allMoviesLink.classList.remove('active');
        allMoviesSection.classList.add('d-none');
        topMoviesSection.classList.remove('d-none');
    }
}

// ===== UTILS =====
function numberWithCommas(number) {
    return number?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") ?? '';
}

function escapeHtml(str) {
    if (!str) return '';
    return String(str).replace(/[&<>"']/g, s => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    })[s]);
}

function showLoading() {
    loading.style.display = 'block';
    error.classList.add('d-none');
}

function hideLoading() {
    loading.style.display = 'none';
}

function showError(e) {
    console.error(e);
    error.classList.remove('d-none');
    error.textContent = 'Failed to load movies. ' + (e.message || e);
}
