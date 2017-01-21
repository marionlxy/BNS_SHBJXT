// JavaScript Document
// hide all bug html tag,include select menu
function EZ_fixIeBug() {
	var EZ_IeBugTag_select = document.getElementsByTagName("select");
	var EZ_IeBugTag_menu = document.getElementsByTagName("menu");

	if (EZ_IeBugTag_select) {
		for (i = 0; i < EZ_IeBugTag_select.length; i++) {
			EZ_IeBugTag_select[i].style.display = "none";
		}
	}
	if (EZ_IeBugTag_menu) {
		for (i = 0; i < EZ_IeBugTag_menu.length; i++) {
			EZ_IeBugTag_menu[i].style.display = "none";
		}
	}
}