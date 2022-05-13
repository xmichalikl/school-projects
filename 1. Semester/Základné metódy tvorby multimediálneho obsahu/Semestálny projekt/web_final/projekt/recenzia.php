	<?php include 'inc/header.php'; ?>
	


	
	<section>

		<h1 class="title"><?php echo $texts->nadpis ?></h1>

		<div class="hodnotenie_a_cena">

			<img class="recenzia_foto" src="media/img/others/front.png" alt="">
			
			<h1><?php echo $texts->nadpis2 ?></h1>
			<img class="hodnotenie_foto" src="media/gif/hodnotenie.gif" alt="">
			<h2><?php echo $texts->nadpis3 ?></h2>
			<h3><?php echo $texts->nadpis4 ?></h3>
		
		</div>

		<table class="recenzia_tab" border="0" >
				
				<tr>
					<td><?php echo $texts->tabulka[0] ?></td>
					<td><?php echo $texts->tabulka[1] ?></td>
					<td><?php echo $texts->tabulka[2] ?></td>
				</tr>

				<tr>
					<td><?php echo $texts->tabulka[3] ?></td>
					<td><?php echo $texts->tabulka[4] ?></td>
					<td><?php echo $texts->tabulka[5] ?></td>
				</tr>

		</table>


	</section>

	
<?php include 'inc/footer.php'; ?>