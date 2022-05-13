<?php include 'inc/header.php'; ?>	

	<section>
	
		<h1 class="title"><?php echo $texts->nadpis ?></h1>

		<section class="gallery">

			<?php 

				$albums = glob("media/img/gallery/*");

				foreach( $albums as $album ){

					echo '<div class="album">';
					
						$photos = glob($album."/*");

						foreach ($photos as $photo) {
							echo '<a href="'. $photo .'"><img src="'. $photo .'"></a>';
						}

					echo '</div>';

				}
				
			?>

		</section>


	</section>

	

	<?php include 'inc/footer.php'; ?>