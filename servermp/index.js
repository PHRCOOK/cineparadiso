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
app.use(express.urlencoded({ extend: true }));

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
        success: "https://www.google.com/",
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
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader(
    "Access-Control-Allow-Methods",
    "GET, OPTOIONS, PATCH, DELETE, POST, PUT"
  );
  res.sendStatus(200);
});

app.listen(port, () => {
  console.log(`Servidor en el puerto ${port}`);
});
