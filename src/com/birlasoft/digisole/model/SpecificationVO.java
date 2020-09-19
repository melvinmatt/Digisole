package com.birlasoft.digisole.model;

public class SpecificationVO extends ProductVO {

	private String specID;
	private String processorBrand;
	private String processor;
	private String storageType;
	private String ram;
	private String rom;
	private String os;
	private String color;
	private String seller;
	private String other;
	
	public SpecificationVO() {
		super();
	}
	
	public SpecificationVO(String prodID, String name, String specID, double cost, float rating, String offer,
			String type, String forType) {
		super(prodID, name, specID, cost, rating, offer, type, forType);
	}


	public SpecificationVO(String specID, String processorBrand, String processor, String ram, String rom, String os, String color, String seller, String other) {
		super();
		this.specID = specID;
		this.processorBrand = processorBrand;
		this.processor = processor;
		this.ram = ram;
		this.rom = rom;
		this.os = os;
		this.color = color;
		this.seller = seller;
		this.other = other;
	}

	public String getSpecID() {
		return specID;
	}

	public void setSpecID(String specID) {
		this.specID = specID;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getProcessorBrand() {
		return processorBrand;
	}

	public void setProcessorBrand(String processorBrand) {
		this.processorBrand = processorBrand;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return getType() + ": " + getName() +
				"\nCost: " + getCost() + 
				"\nRating: " + getRating() + "/5" +
				"\nOffer: " + getOffer() +
				"\n********Specification*********\n" + 
				"Processor: " + processor +
				"\nStorage Type: " + storageType +
				"\nRAM: " + ram +
				"\nROM: " + rom + 
				"\nOperating System: " + os +
				"\nHighlight: " + other +
				"\nColor: " + color + 
				"\nSeller:" + seller;
	}
	
}
