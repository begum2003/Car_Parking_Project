import cv2

def process_image(image_path):
    image = cv2.imread(image_path)
    if image is not None:
        # Perform image processing operations here
        # For example, you can display the image using cv2.imshow()
        cv2.imshow("Image", image)
        cv2.waitKey(0)
        cv2.destroyAllWindows()
    else:
        print("Failed to read image")