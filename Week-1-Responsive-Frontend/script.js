const root = document.documentElement;
const header = document.querySelector('.site-header');
const navToggle = document.getElementById('navToggle');
const navMenu = document.getElementById('navMenu');
const themeToggle = document.getElementById('themeToggle');
const backToTop = document.getElementById('backToTop');
const form = document.getElementById('contactForm');
const formStatus = document.getElementById('formStatus');
const year = document.getElementById('year');
const revealItems = document.querySelectorAll('.reveal');
const counters = document.querySelectorAll('.counter');
const testimonialCards = document.querySelectorAll('.testimonial-card');
const dots = document.querySelectorAll('.dot');
const prevTestimonial = document.getElementById('prevTestimonial');
const nextTestimonial = document.getElementById('nextTestimonial');
const faqItems = document.querySelectorAll('.faq-item');

let testimonialIndex = 0;
let theme = localStorage.getItem('theme') || 'dark';

function applyTheme(mode) {
  root.setAttribute('data-theme', mode);
  themeToggle.querySelector('.theme-toggle__icon').textContent = mode === 'dark' ? '☀️' : '🌙';
  themeToggle.setAttribute('aria-pressed', String(mode === 'dark'));
  localStorage.setItem('theme', mode);
}

function toggleMobileMenu() {
  const expanded = navToggle.getAttribute('aria-expanded') === 'true';
  const nextState = String(!expanded);
  navToggle.setAttribute('aria-expanded', nextState);
  navMenu.classList.toggle('open', nextState === 'true');
}

function closeMobileMenu() {
  navToggle.setAttribute('aria-expanded', 'false');
  navMenu.classList.remove('open');
}

function handleScroll() {
  header.classList.toggle('scrolled', window.scrollY > 24);
  backToTop.classList.toggle('visible', window.scrollY > 700);
}

function revealOnScroll() {
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add('is-visible');
          observer.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.15 }
  );

  revealItems.forEach((item) => observer.observe(item));
}

function animateCounters() {
  const counterObserver = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (!entry.isIntersecting) return;

        const el = entry.target;
        const target = Number(el.dataset.target || 0);
        const suffix = el.dataset.suffix || '';
        const prefix = el.dataset.prefix || '';
        const duration = 1200;
        const startTime = performance.now();

        const update = (time) => {
          const progress = Math.min((time - startTime) / duration, 1);
          const currentValue = Math.floor(progress * target);
          el.textContent = `${prefix}${currentValue}${suffix}`;
          if (progress < 1) {
            requestAnimationFrame(update);
          } else {
            el.textContent = `${prefix}${target}${suffix}`;
          }
        };

        requestAnimationFrame(update);
        counterObserver.unobserve(el);
      });
    },
    { threshold: 0.6 }
  );

  counters.forEach((counter) => counterObserver.observe(counter));
}

function showTestimonial(index) {
  testimonialIndex = (index + testimonialCards.length) % testimonialCards.length;
  testimonialCards.forEach((card, cardIndex) => {
    card.classList.toggle('active', cardIndex === testimonialIndex);
  });
  dots.forEach((dot, dotIndex) => {
    dot.classList.toggle('active', dotIndex === testimonialIndex);
  });
}

function nextSlide() {
  showTestimonial(testimonialIndex + 1);
}

function prevSlide() {
  showTestimonial(testimonialIndex - 1);
}

function setupFaq() {
  faqItems.forEach((item) => {
    const button = item.querySelector('.faq-question');
    button.addEventListener('click', () => {
      const isOpen = item.classList.contains('active');
      faqItems.forEach((faq) => {
        faq.classList.remove('active');
        faq.querySelector('.faq-question').setAttribute('aria-expanded', 'false');
      });
      if (!isOpen) {
        item.classList.add('active');
        button.setAttribute('aria-expanded', 'true');
      }
    });
  });
}

function validateField(field) {
  const name = field.name;
  const value = field.value.trim();
  const errorEl = document.querySelector(`[data-error-for="${name}"]`);

  if (name === 'name' && value.length < 2) {
    errorEl.textContent = 'Please enter your full name.';
    return false;
  }

  if (name === 'email') {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(value)) {
      errorEl.textContent = 'Please enter a valid email address.';
      return false;
    }
  }

  if (name === 'message' && value.length < 10) {
    errorEl.textContent = 'Please share a bit more detail about your project.';
    return false;
  }

  errorEl.textContent = '';
  return true;
}

function validateForm() {
  const fields = Array.from(form.querySelectorAll('input, textarea'));
  let isValid = true;

  fields.forEach((field) => {
    const fieldValid = validateField(field);
    if (!fieldValid) {
      isValid = false;
    }
  });

  return isValid;
}

function handleSubmit(event) {
  event.preventDefault();
  if (!validateForm()) {
    formStatus.textContent = 'Please correct the highlighted fields and try again.';
    return;
  }

  formStatus.textContent = 'Thanks! Your request has been received and we will follow up shortly.';
  form.reset();
  form.querySelectorAll('.error-message').forEach((error) => {
    error.textContent = '';
  });
}

function autoRotateTestimonials() {
  if (testimonialCards.length < 2) return;
  window.setInterval(() => {
    showTestimonial(testimonialIndex + 1);
  }, 6000);
}

applyTheme(theme);
year.textContent = new Date().getFullYear();
window.addEventListener('scroll', handleScroll, { passive: true });
handleScroll();
revealOnScroll();
animateCounters();
showTestimonial(0);
setupFaq();
autoRotateTestimonials();

navToggle.addEventListener('click', toggleMobileMenu);
navMenu.querySelectorAll('a').forEach((link) => link.addEventListener('click', closeMobileMenu));
window.addEventListener('resize', () => {
  if (window.innerWidth > 760) {
    closeMobileMenu();
  }
});

themeToggle.addEventListener('click', () => {
  const nextTheme = root.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
  applyTheme(nextTheme);
});

backToTop.addEventListener('click', () => {
  window.scrollTo({ top: 0, behavior: 'smooth' });
});

prevTestimonial.addEventListener('click', prevSlide);
nextTestimonial.addEventListener('click', nextSlide);
dots.forEach((dot) => {
  dot.addEventListener('click', () => showTestimonial(Number(dot.dataset.slide)));
});

form.querySelectorAll('input, textarea').forEach((field) => {
  field.addEventListener('input', () => validateField(field));
});
form.addEventListener('submit', handleSubmit);
