<?php 

	function clear($str){
		$str = trim($str);
		$str = htmlspecialchars($str);
		$str = filter_var($str, FILTER_SANITIZE_STRING);
		return $str;
	}





	function get_all_texts(){
		$texts = file_get_contents("inc/texts.json");
		$texts = json_decode($texts);

		return $texts;
	}





	function get_page_texts($page, $lang){
		$all_texts = get_all_texts();
		
		if( $lang != "sk" && $lang != "en" ) $lang = "sk";
		
		foreach ($all_texts as $page_texts) {
			if( $page_texts->stranka == $page ) return $page_texts->$lang;
		}

		return $all_texts[0];
	}