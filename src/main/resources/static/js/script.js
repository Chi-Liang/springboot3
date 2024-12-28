'use strict';
$(function () {
	const $mobile_menu = $(".mobile_menu");
	const $mobile_header_nav = $(".mobile_header_nav");
	const $rule_detail_btn = $(".rule_detail_btn").find("li");

	//Loading
	$("#loading_mask").show();
	$("#wrapper").css("opacity", "0");
	$(window).on("load", function () {
		setTimeout(stop_load, 700);
		setTimeout(content_box, 700);
	});
	function stop_load() {
		$("#loading_mask > p").fadeOut(700);
		$("#loading_mask").delay(500).fadeOut(1400);
	}
	function content_box() {
		$("#wrapper").css("opacity", "1");
	}

	//Mobile Menu
	$mobile_menu.click(function () {
		$(this).toggleClass("open");
	});
	$mobile_menu.on("click", menu_open);
	function menu_open() {
		$(".mobile_header").toggleClass("mobile_header_shadow");
		$mobile_header_nav.slideToggle();
	}
	$(".mobile_header_nav").find("li").find("a.member_login_btn,a.member_contact_btn").on("click", function () {
		$mobile_header_nav.hide();
		$(".mobile_menu").removeClass("open");
	});
	$(window).on("resize", menu_style_clear);
	function menu_style_clear() {
		if ($(window).innerWidth() > 1024) {
			$mobile_header_nav.attr("style", "");
		}
	}

	//TEAM Slider
	$(".sliderBtn li").on("click", function () {
		$(this).addClass("active").siblings().removeClass("active");
		$(this).find("i").addClass("active").siblings().removeClass("active");
		$(".professionTeamText").find("li").length;
	});

	var current = 0;
	var slideTotal = $(".professionTeamText li").length;
	var slideTotal = slideTotal - 1;
	var slideTotalMath = Math.abs(slideTotal) * - 1;
	$(".professionTeamText li").hide();
	$(".professionTeamText li:first").fadeIn();
	$(".nextBtn").on("click", function () {
		if (current >= slideTotal) {
			current = 0;
		} else {
			current++;
		}
		$(".professionTeamText li").hide();
		$(".professionTeamText li").eq(current).fadeIn();
	});

	$(".prevBtn").on("click", function () {
		if (current < slideTotalMath) {
			current = 0;
		}
		current--;
		$(".professionTeamText li").hide();
		$(".professionTeamText li").eq(current).fadeIn();
	});

	//Modal
	$(".member_login_btn").on("click", function () {
		$(".mask").show();
		$(".loginModal").show();
	});
	$(".member_contact_btn").on("click", function () {
		$(".mask").show();
		$(".contactModal").show();
	});
	$(".forget_psw").on("click", function () {
		$(".loginModal").hide();
		$(".forgetPswModal").show();
	});
	$(".modal_close_icon").on("click", function () {
		$(".loginModal,.forgetPswModal,.registerRuleModal,.registerModal,.contactModal").hide();
		$(".mask").hide();
	});
	$(".register_btn").on("click", function () {
		$(".mask").show();
		$(".registerRuleModal").show();
	});
	$(".add_member").on("click", function () {
		$(".registerRuleModal").show();
	});
	$(".agree_btn").on("click", function () {
		$(".registerModal").show();
	});
	$(".disagree_btn").on("click", function () {
		$(".loginModal,.registerRuleModal").hide();
		$(".mask").hide();
	});

	//Rule Btn
	$(".rule_detail_btn").find("li").on("click", function () {
		let $rule_detail_btn_place = $(this).find("a").attr("data-target");
		$rule_detail_btn.removeClass("selected");
		$(this).addClass("selected");
		$("html,body").stop().animate({
			scrollTop: $("#" + $rule_detail_btn_place).offset().top - 92
		}, 800);
		return false;
	});

	//Tabs
	$("ul.speech_tab_btn").find("li").on("click", function () {
		let $now_speech_tab = $(this).find("a").attr("href");
		$("ul.speech_tab_btn").find("li").removeClass("selected");
		$(this).toggleClass("selected");
		$(".speech_tab_content[class!='speech_tab_content_1']").hide();
		$(this).addClass("speech_tab_current").siblings().removeClass("speech_tab_current");
		$($now_speech_tab).fadeIn(300);
		return false;
	});

	//Analysis
	function analysis_subtract() {
		let $invoice = parseFloat($("#invoice").val());
		let $expenditure = parseFloat($("#expenditure").val());
		let $car_debt = parseFloat($("#car_debt").val());
		let $house_debt = parseFloat($("#house_deb").val());
		let $card_debt = parseFloat($("#card_debt").val());
		let $monthly_balance_result = $invoice - $expenditure - $car_debt - $house_debt - $card_debt;
		$("#monthly_balance_result").val($monthly_balance_result);
	}
	$(".analysis_debt_btn").on("click", function () {
		analysis_subtract();
	});

	//GoTopBtn
	$("#goTopBtn").on("click", function () {
		$("html,body").stop().animate({ scrollTop: 0 }, 800);
		return false;
	});

	//Slider
	$(".index_slider").slick({
		slidesToShow: 1,
		arrows: false,
		autoplay: true,
		dots: true,
		fade: true,
		infinite: true,
		speed: 2000
	});
	$('.owl-carousel').owlCarousel({

		responsive: {
			0: {
				items: 1,

			},
			820: {
				items: 2,

			},

		}
	});
	$(document).ready(function () {
		// 初始化 Owl Carousel
		$(".owl-carousel").owlCarousel();

		// 找到預設的按鈕並隱藏它們
		$(".owl-nav").hide();

		// 點擊自訂的 next 按鈕時，移動到下一個項目
		$(".custom-next-btn").click(function () {
			$(".owl-carousel").trigger('next.owl.carousel');
		});

		// 點擊自訂的 previous 按鈕時，移動到上一個項目
		$(".custom-prev-btn").click(function () {
			$(".owl-carousel").trigger('prev.owl.carousel');
		});
	});

});
// 增加商品數量
function incrementQuantity() {
	var quantityInput = document.getElementById('quantity');
	var currentQuantity = parseInt(quantityInput.value);
	quantityInput.value = currentQuantity + 1;
}

// 減少商品數量
function decrementQuantity() {
	var quantityInput = document.getElementById('quantity');
	var currentQuantity = parseInt(quantityInput.value);
	if (currentQuantity > 1) {
		quantityInput.value = currentQuantity - 1;
	}
}
function toggleContent(element) {
	var content = element.nextElementSibling;
	var button = element.querySelector('.toggleButton');

	// 檢查目前是否展開
	var isExpanded = content.style.maxHeight !== "0px";

	// 關閉其他相同的區塊
	var allBlocks = document.querySelectorAll('.itemblock');
	allBlocks.forEach(function (item) {
		var blockContent = item.querySelector('.content');
		var blockButton = item.querySelector('.toggleButton');

		if (blockContent !== content) {
			blockContent.style.maxHeight = "0";
			blockButton.innerHTML = "查看訂單詳情▼";
		}
	});

	// 切換展開狀態
	if (isExpanded) {
		content.style.maxHeight = "0";
		button.innerHTML = "查看訂單詳情▼";
	} else {
		// 計算內容的高度並應用
		content.style.maxHeight = content.scrollHeight + "px";
		button.innerHTML = "關閉訂單詳情▲";
	}
}
document.addEventListener('DOMContentLoaded', function () {
	// 獲得 div1 和 div2 的元素
	var div1 = document.getElementById('div1');
	var div2 = document.getElementById('div2');

	// 檢查div2是否存在
	if (div2) {
		// 定義函數來更新 div1 的高度
		function updateDiv1Height() {
			var div2Height = div2.offsetHeight; // 獲得 div2 的高度
			div1.style.height = (div2Height + 200) + 'px'; // 设置 div1 的高度为 div2 的高度加上 200px
		}

		// 初始化时调用一次，确保初始高度是正确的
		updateDiv1Height();

		// 监听 div2 的高度变化
		// 可以使用 ResizeObserver 或 MutationObserver 进行监听
		var observer = new ResizeObserver(updateDiv1Height);
		observer.observe(div2);
	}
});
function toggleMobileDropdown() {
	var dropdown = document.getElementById("mobileDropdown");
	dropdown.classList.toggle("show");
}

// 修改此函數來避免在一開始就顯示子選單
function toggleDesktopDropdown(open) {
	var dropdown = document.getElementById("desktopDropdown");
	if (open) {
		dropdown.classList.add("show");
	} else {
		dropdown.classList.remove("show");
	}
}

document.addEventListener("DOMContentLoaded", function() {
 var foot = document.getElementById("myFoot");
 foot.style.display = "block";
});