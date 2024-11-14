package com.tecsup.petclinic.util;

import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.domain.PetTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

	public static Pet getPet() {
		return new Pet(1, "Leo", 1, 1, null);
	}

	public static Pet newPet() {
		return new Pet(0, "Punky", 1, 1, null);
	}

	public static Pet newPetCreated() {
		Pet pet = newPet();
		pet.setId(1000);
		return pet;
	}

	public static Pet newPetForUpdate() {
		return new Pet(0, "Bear", 1, 1, null);
	}

	public static Pet newPetCreatedForUpdate() {
		Pet pet = newPetForUpdate();
		pet.setId(4000);
		return pet;
	}

	public static Pet newPetForDelete() {
		return new Pet(0, "Bird", 1, 1, null);
	}

	public static Pet newPetCreatedForDelete() {
		Pet pet = newPetForDelete();
		pet.setId(2000);
		return pet;
	}

	public static List<PetTO> getAllPetTOs() {
		List<PetTO> petTOs = new ArrayList<>();
		petTOs.add(new PetTO(1, "Leo", 1, 1, "2000-09-07"));
		petTOs.add(new PetTO(2, "Basil", 6, 2, "2002-08-06"));
		petTOs.add(new PetTO(3, "Rosy", 2, 3, "2001-04-17"));
		petTOs.add(new PetTO(4, "Jewel", 2, 3, "2000-03-07"));
		petTOs.add(new PetTO(5, "Iggy", 3, 4, "2000-11-30"));
		return petTOs;
	}

	public static List<OwnerTO> getAllOwnerTOs() {
		List<OwnerTO> ownerTOs = new ArrayList<>();
		ownerTOs.add(new OwnerTO(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023"));
		ownerTOs.add(new OwnerTO(2, "Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749"));
		ownerTOs.add(new OwnerTO(3, "Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763"));
		ownerTOs.add(new OwnerTO(4, "Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198"));
		ownerTOs.add(new OwnerTO(5, "Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765"));
		ownerTOs.add(new OwnerTO(6, "Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654"));
		ownerTOs.add(new OwnerTO(7, "Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387"));
		ownerTOs.add(new OwnerTO(8, "Maria", "Escobito", "345 Maple St.", "Madison", "6085557683"));
		ownerTOs.add(new OwnerTO(9, "David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435"));
		ownerTOs.add(new OwnerTO(10, "Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487"));
		return ownerTOs;
	}

	public static OwnerTO getOwnerTO() {
		return new OwnerTO(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
	}

	public static OwnerTO newOwnerTO() {
		return new OwnerTO(-1, "John", "Doe", "1234 Elm St.", "Springfield", "5551234567");
	}

	public static OwnerTO newOwnerTOForDelete() {
		return new OwnerTO(10000, "Jane", "Doe", "4321 Oak St.", "Riverside", "5559876543");
	}

	public static List<Pet> getPetsForFindByName() {
		List<Pet> pets = new ArrayList<>();
		pets.add(new Pet(1, "Leo", 1, 1, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByTypeId() {
		List<Pet> pets = new ArrayList<>();
		pets.add(new Pet(9, "Lucky", 5, 7, null));
		pets.add(new Pet(11, "Freddy", 5, 9, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByOwnerId() {
		List<Pet> pets = new ArrayList<>();
		pets.add(new Pet(12, "Lucky", 2, 10, null));
		pets.add(new Pet(13, "Sly", 1, 10, null));
		return pets;
	}

	public static PetTO getPetTO() {
		return new PetTO(1, "Leo", 1, 1, "2000-09-07");
	}

	public static PetTO newPetTO() {
		return new PetTO(-1, "Beethoven", 1, 1, "2020-05-20");
	}

	public static PetTO newPetTOForDelete() {
		return new PetTO(10000, "Beethoven3", 1, 1, "2020-05-20");
	}
}
