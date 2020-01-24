
//To prevent back button press by user

history.pushState(null, null, document.URL);
window.addEventListener('popstate', function(){
	history.pushState(null, null, document.URL);
});