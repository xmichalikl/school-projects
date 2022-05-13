<?php 
	require 'functions.php';
	$lang = (isset($_GET['lang'])) ? $_GET['lang'] : "sk"; 

	$active_page_path = explode('/', $_SERVER["PHP_SELF"]);
	$active_page = array_pop($active_page_path);

	$texts = get_page_texts($active_page, $lang);
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title><?php echo $texts->nadpis ?> - DZ Phone Reviews</title>
</head>
<body>

	

	<header>
		
		<div class="info-bar">
			
			<p></p>

		</div>
		
		<nav>
	
			<ul class="menu">
				
				<?php foreach (get_all_texts() as $menu_texts): ?>
					<li><a href="<?php echo $menu_texts->stranka .'?lang='. $lang ?>"><?php echo $menu_texts->$lang->nadpis ?></a></li>
				<?php endforeach ?>

			</ul>

		</nav>

		<div class="language">

			<a href="?lang=sk"<?php echo ($lang == "sk") ? ' class="active"' : "" ?>>SK</a>
			<a href="?lang=en"<?php echo ($lang == "en") ? ' class="active"' : "" ?>>EN</a>
			
		</div>	

	</header>