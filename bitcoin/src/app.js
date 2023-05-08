const { createWallet, getBalance, send } = require("./wallet");

const start = async () => {
  //   const wallet = createWallet();
  //   console.log(wallet);

  //   const balance = await getBalance("mwRoSmns2iWowMmsYAxwDukyTgaDx3qvGj");
  //   console.log("wallet balance : ", balance);

  send("mogiyYzT98HWbXW4bDhpDg5VJBtWDBLUgS", 100000);
};

start();
