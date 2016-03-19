package Graphics;

public class Camera {

    public Camera() {
        target_x = 0.0f;
        camara_x = 0.0f;
        camaraUP_x = 0.0f;
        target_y = -0.0f;
        camara_y = 10.0f;
        camaraUP_y = 1.0f;
        target_z = 0.0f;
        camara_z = 1000.0f;
        camaraUP_z = 0.0f;
        Radius = 250;
        theta = 0.0f;
        phi = 1.5f;
        lx = 0.0f;
        lz = 0.0f;
        ly = 0.0f;
    };

    public void Rotate(float pitch) {
        theta += -1 * (pitch);
        if (theta >= 6.28318) theta -= 6.28318;
        if (theta <= 0) theta += 6.28318;
    };

    public void Elevate(float yaw) {
        // Calculate the yaw (keep it within 0-pi/2)
        phi += yaw;
        if (phi >= pi - .0001) phi = pi - .0001f;
        if (phi <= .0001) phi = .0001f;
    };

    public void Zoom(float radius) {
        // Update the camDistance
        Radius += radius;

        if (Radius <= 1) Radius = 1;
        if (Radius >= 4000) Radius = 4000;
    };

    public void Update() {
        float tx = (target_x - camara_x);
        float tz = (target_z - camara_z);

        float magnitude = (float) Math.sqrt(tx * tx + tz * tz);

        if (magnitude > 0) {
            tx = tx / magnitude;
            tz = tz / magnitude;
        }

        lx = (float) Math.cos(theta) * (float) Math.sin(phi);
        lz = (float) Math.sin(theta) * (float) Math.sin(phi);
        ly = (float) Math.cos(phi);

        camara_x = target_x + lx * Radius;
        camara_z = target_z + lz * Radius;
        camara_y = target_y + ly * Radius;

    };

    public void Move(float x, float y, float z) {
        float tx = (target_x - camara_x);
        float tz = (target_z - camara_z);

        float magnitude = (float) Math.sqrt(tx * tx + tz * tz);

        if (magnitude > 0) {
            tx = tx / magnitude;
            tz = tz / magnitude;
        }

        target_x += x * (tx);
        target_z += x * (tz);
        target_x += z * (tz);
        target_z += z * (-1 * tx);
    };

    float target_x, target_y, target_z;
    float camara_x, camara_y, camara_z;
    float camaraUP_x, camaraUP_y, camaraUP_z;

    float theta, phi, lx, lz, ly, Radius;
    float pi = 3.141592f;

}
