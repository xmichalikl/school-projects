<?php include 'inc/header.php'; ?>

	<?php 

		$errors = array(
			"Nezadal si meno",
			"Nezadal si spravu"
		);

		if( isset( $_POST['ok'] ) ){

			$meno = clear( $_POST['meno'] );
			$sprava = clear( $_POST['sprava'] );

			$error = 0;

			if( empty( $meno ) ) $error = 1;
			else if( empty( $sprava ) ) $error = 2;
			else {

				$sprava = array(
					"meno" => $meno,
					"sprava" => $sprava
				);

				$komentare = file_get_contents("inc/komentare.json");
				$komentare = json_decode($komentare);
				array_push($komentare, $sprava);
				$komentare = json_encode($komentare);
				file_put_contents("inc/komentare.json", $komentare);

			}
		}

	?>
	
	<section>

		<h1 class="title"><?php echo $texts->nadpis ?></h1>

		<div class="novy_komentar">
			
			<h2><?php echo $texts->nadpis2 ?></h2>

			<form action="" method="POST">
				<div class="row">
					<input type="text" name="meno" autofocus placeholder="Meno"<?php echo (isset($error) && $error) ? ' value="'. $_POST['meno']. '"' : "" ?>>
					</div>
				<textarea rows="10" class="row" name="sprava" placeholder="Zadaj text"><?php echo (isset($error) && $error) ? $_POST['sprava'] : "" ?></textarea>
				<div class="row">
					<input type="submit" value="Potvrdiť" name="ok">
				</div>
			</form>

			<p class="hlasenie">
				<?php 
					if( isset( $error ) )
						if( $error ) echo $errors[ $error - 1 ];
						else echo "Komentár bol úspešne pridaný";
				?>
			</p>

		</div>


		<div class="predosle_komentare">
			
			<h2><?php echo $texts->nadpis3 ?></h2>

			<?php

				$komentare = file_get_contents("inc/komentare.json");
				$komentare = json_decode($komentare);

				foreach ($komentare as $komentar) {
					echo '<article class="predosly_komentar">';
						echo '<h4>'. $komentar->meno .'</h4>';
						echo '<p>'. $komentar->sprava .'</p>';
					echo '</article>';
				}

			?>

		</div>

	</section>

	

<?php include 'inc/footer.php'; ?>