/*소셜로그인 버튼들 숨겨놨다가 클릭하면 나오게 하기*/
const loginApiText = document.querySelector('.login_api_text');
const loginApi = document.querySelector('.login_api');

loginApiText.addEventListener('click', () => {
  if (loginApi.style.display === 'flex') {
    loginApi.style.display = 'none';
  } else {
    loginApi.style.display = 'flex';
  }
});