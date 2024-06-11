import express from "express";
import cors from "cors";
import { MercadoPagoConfig, Preference } from "mercadopago";

const client = new MercadoPagoConfig({
  accessToken:
    "TEST-1560459330000148-052920-5388e2325772716dc3f29b6b8ff41085-154386961",
});

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const sendPreferencesToUpdateTableDB = (preferences) => {
  if (preferences.date && preferences.hour && preferences.seats.length > 0) {
    fetch("https://api-cine-paradiso.vercel.app/update-seatsdateshours", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(preferences),
    })
      .then(() => console.log("Preferences updated successfully")) // Cambia a un manejo adecuado de éxito
      .catch((err) => console.error("Error updating preferences:", err)); // Cambia a un manejo adecuado de errores
  }
};

app.post("/create_preference", async (req, res) => {
  try {
    const { title, quantity, price } = req.body;
    console.log("Title:", title);
    console.log("Quantity:", quantity);
    console.log("Unit Price:", price);
    const idempotencyKey = req.headers["x-idempotency-key"];

    const body = {
      items: [
        {
          title: title,
          quantity: Number(quantity),
          unit_price: Number(price) / quantity,
          currency_id: "ARS",
        },
      ],
      back_urls: {
        success: "https://tu-servidor.com/update-table", // URL de éxito modificada
        failure: "https://www.google.com/",
        pending: "https://www.google.com/",
      },
      auto_return: "approved",
    };

    const preference = new Preference(client);
    const result = await preference.create({ body, idempotencyKey });
    res.json({
      id: result.id,
    });
  } catch (error) {
    console.log(error);
    res.status(500).json({ error: "Error al crear la preferencia" });
  }
});

app.options("/", (req, res) => {
  res.setHeader(
    "Access-Control-Allow-Origin",
    "https://servermp-jhgwgsrnl-pablo-rubios-projects.vercel.app"
  );
  res.setHeader(
    "Access-Control-Allow-Methods",
    "GET, OPTIONS, PATCH, DELETE, POST, PUT"
  );
  res.sendStatus(200);
});

app.listen(port, () => {
  console.log(`Servidor en el puerto ${port}`);
});

app.post("/update-table", (req, res) => {
  sendPreferencesToUpdateTableDB(req.body); // Aquí ejecuta la función con los datos del body
  res.sendStatus(200); // Envía una respuesta exitosa al cliente
});
