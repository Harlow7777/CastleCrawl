# CastleCrawl
Java Swing based roguelike dungeon crawler

This project evolved from a pen and paper design.
Starts with 10x10 cell(floor 1). Each cell is randomly generated upon entering.
Each cell also randomly spawns an encounter(monster, shop, trap, or nothing)
Combat is turn based and each button click is random number 0-6 against the opponents.
Currently has multi floor support, combat, navigation, shop, inventory

		//TODO: add monster skills
		//TODO: add functionality to inventory items(show equipped items 		as jtextarea)
		//TODO: add hiscore recording
		//TODO: bugfix for restart after trap death
		//TODO: bugfix second floor "P" at start cell not persisting
		//TODO: add champion recording(10th floor)
		//TODO: add corruption consequence(reach 6 - become enemy at 		that square)