package kiosk;

public class CafeMenuVO {
	private String productName;
	private int categoryId;
	private int productPrice;
	private String productPicture;
	private String productDescription;

	public CafeMenuVO() {
	}

	public CafeMenuVO(String productName, int categoryId, int productPrice, String productPicture,
			String productDescription) {

		this.categoryId = categoryId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPicture = productPicture;
		this.productDescription = productDescription;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setFoodPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

}
