	<?php include 'inc/header.php'; ?>
	<section>
		<h1 class="title">Ray Tracing</h1>
		<div class="raytracing">
			<div class="block" >
				<div class="raytracing_center">
					<h3>Rasterizácia</h3>
					<p>Pre vysvetlenie Ray Tracingu (sledovania lúčov), je potrebné si najskôr povedať, čo je to “rasterizácia”, teda predchodca Ray Tracingu. Rasterizácia je technika, kde sa po vyrenderovaní objektov (najčastejšie trojuholníkov) grafickou kartou scéna “rasterizuje”. To znamená že vyrenderované objekty sa presunú na 2D plochu. Tato plocha je o veľkosti pixelov našej obrazovky (Full HD-1920x1080 pixelov). Na tejto 2D ploche sú vyrenderované objekty spracovane “shader”-om. Toto ovplyvňuje farbu, textúru aj tieňovanie. Výsledok tohto procesu je jedna snímka. Znie to veľmi dobre ale shader, len odhaduje svetlo ktoré pôsobí na objekt. Samozrejme tento odhad je relatívne presný ale nie je veľmi realisticky a má svoje limity.  Veľkou výhodou tohto spôsobu je ale mala záťaž na hardware, čo vysvetľuje prečo je tento proces používaný najmä pre real-time operácie. Tu prichádza Ray tracing (Sledovanie lúčov).</p><br>
					<h3>Čo je to Ray Tracing?</h3>
					<p>Poďme si najprv vysvetliť ako to funguje. Mame  vyrenderovanú guľôčku (ktorá neprepúšťa svetlo) nad ktorou je zdroj svetla. Z nášho zdroja ide obrovské množstvo svetelných lúčov. Každý jeden z tých svetelných lúčov budeme nasledovať a pozerať sa, od čoho kam sa odrazí, cez čo a v akej intenzite prejde. V našom prípade sa lúče budú odrážať od guličky. Na plochu kam lúče nedopadli vôbec, bude tieň. Po vygenerovaní všetkých odrazov a dopadov lúčov, vieme povedať kde a koľko je. Teraz stačí povedať z kade sa chceme na guličku pozerať a mame jeden farme. Tento proces je však veľmi náročný a prakticky sa nedá hovoriť o použití v real-time scenaroch. Pouziva sa hlavne pri tvoreni filmov, kde generovanie jedneho frame-u moze trvat kludne aj niekolko hodin.</p>
				</div>
			</div>
			<div class="block">
				<div class="img_left">
					<img class="porovnanie" src="../../media/img/Ray_trace_diagram.png" alt="">
				</div>
				<div class="raytracing_right">
					<h3>Real Time Ray Tracing</h3>
					<p>Našťastie máme aj druhý typ sledovania lúčov ktorý je síce oveľa náročnejší ako rasterizácia, ale stále sa da hovoriť o použití v real time scenaroch. Ide o veľmi podobný princíp ako pri prvom type sledovania lúčov, ale postup samotného “sledovania” lúčov je opačný.<br><br>

					Pri prvom type sme šli postupom:<br>
					(vyrenderovanie 3D objektu, sledovanie lúčov od zdroja svetla, uvedomenie si ako sa na objekt pozeráme, vygenerovanie frame-u)<br><br>

					Teraz pôjdeme spôsobom:<br>
					(vyrenderovanie 3D objektu, uvedomenie si, ako sa na objekt pozeráme, sledovanie len tých lúčov, ktoré nás zaujímajú, vygenerovanie frameu)<br><br>

					Vysvetlím “sledovanie len tých lúčov čo nás zaujímajú”. Ako som povedal, nás monitor ma určitý počet pixelov (Full HD-1920x1080 pixelov) a viac pixelov nevidíme.<br><br>
					Nám stačí, ak sa vytieňuje každý pixel, ktorý vidíme, netreba nám viac. Takže každému pixelu z pohľadu z ktorého sa pozeráme pridelíme lúč, ktorý dopadá na teleso od ktorého sa odráža.<br>
					Ak sa tento lúč odrazí priamo do zdroja svetla, bude veľmi svetlý. Ak sa tento lúč poodráža tak, že si zdroj svetla nenájde, budeme vedieť že je v tieni.<br><br>
					Týmto sa značne šetrí čas vygenerovania jedneho frame-u, preto môžeme hovoriť o real time používaní – Real Time Ray Tracing.
					</p>
				</div>
			</div>
			<div class="block">
				<div class="raytracing_center">
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quisquam vitae provident in nemo placeat ullam nobis omnis, consequatur, quaerat cum eos aperiam optio, hic deserunt quas enim corporis, minima! Dolore architecto, eius suscipit? Ea rem adipisci ipsum. Ratione, id reprehenderit aperiam inventore qui, quo. Mollitia distinctio harum eveniet nostrum, beatae non maiores atque sequi nemo quisquam aut blanditiis perspiciatis, sapiente omnis est, optio minima quaerat rerum. Alias neque, saepe? Quisquam voluptates qui ab ducimus aliquid, nobis mollitia reiciendis, id ea, eveniet dicta veritatis molestias. Natus aliquam ipsum earum expedita nostrum deleniti pariatur est veniam, aspernatur consectetur error placeat asperiores similique dignissimos, perspiciatis delectus velit fugiat voluptatem molestiae magni veritatis rem suscipit sapiente minima. Incidunt quis deleniti voluptate est repellat. At nihil sapiente velit, aliquam nemo tenetur porro quos quod voluptatum temporibus eos ab magnam sit neque praesentium corporis explicabo nulla adipisci placeat ipsam quam alias assumenda nobis. Laborum, delectus, accusamus.</p>
				</div>
			</div>
			<div class="block">
				<div class="raytracing_left">
					<h3>Nadpis 3</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Suscipit maiores magnam reprehenderit molestiae in soluta. Minima voluptatem et doloremque cum qui ut, architecto laborum sit illum quibusdam amet eaque adipisci omnis at laboriosam repellendus ratione inventore quo reiciendis, fugit quam ea. Magni ipsum odio asperiores numquam, dolore quae, minima repellat facilis perferendis accusamus soluta fuga nihil blanditiis inventore autem alias nostrum excepturi quos molestias officia, qui laboriosam. Illum quam at tempora esse! Exercitationem ab eaque doloremque aliquid earum ducimus, quibusdam illum atque aliquam debitis illo quod necessitatibus minima facere, voluptatibus, odio tenetur dignissimos, adipisci sed in nemo velit. Qui possimus eligendi natus quis odit quasi accusamus amet dolor, assumenda neque eius voluptate, pariatur hic ipsa! Placeat eos ut voluptates voluptate nihil nostrum distinctio reiciendis, debitis necessitatibus suscipit veniam voluptas fugiat, incidunt magni. Ipsam error dolores maiores mollitia architecto temporibus voluptate iusto aspernatur qui earum nobis laboriosam, tenetur repellendus quod nostrum ad tempora esse rerum provident quas deserunt nihil! Earum, mollitia, optio! Ex odio quod magnam accusantium a dolor, minima, ad iste quo ipsa eveniet tempore sed laborum dolores, esse sapiente eligendi doloribus distinctio expedita perferendis officia.</p>
				</div>
				<div class="img_right">
					<img class="rtx_on" src="../../media/gif/rtx.gif" alt="">
				</div>
			</div>
			<div class="block">
				<div class="raytracing_center">
					<h3>Nadpis 4</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolorem atque beatae, expedita veniam ullam maiores accusantium eius quas incidunt, omnis inventore velit non sed modi magnam delectus. Explicabo aliquam eius velit iusto cum et assumenda. Delectus ut sed, totam autem maiores perferendis quasi doloremque ratione aspernatur est excepturi iusto debitis aut pariatur quaerat similique possimus quas numquam laudantium, incidunt dolor officiis. Omnis, error earum assumenda molestias repudiandae magni vitae obcaecati dolore autem rerum eius accusantium explicabo veniam nam hic ad fugit quo. Officiis repellendus quae mollitia vel molestiae perferendis illum cum, expedita consequatur. Temporibus sint fugit explicabo, esse eos quas numquam animi recusandae sed, perferendis! Suscipit laboriosam, sint doloribus, illo alias ex nulla deserunt tempora, minima vel obcaecati aliquam quos officia possimus? Voluptatibus ea fuga saepe dolorum accusamus, dignissimos odio sint quo. Dolor rem, magni totam! Hic beatae labore, adipisci officia error inventore repudiandae, accusantium, autem ad, facilis quae tenetur.</p>
				</div>
			</div>
		</div>
	</section>
	<footer>
		<p>© 2020 Projekt ZPS 2020</p>
		<p><a href="mailto:lukasmichalikk@gmail.com">email: lukasmichalikk@gmail.com</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="tel:tel.: +421949747116">tel.: +421 xxx xxx xxx
	</footer>
	<script src="../../js/date_sk.js"></script>
</body>
</html>