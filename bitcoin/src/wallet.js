const axios = require("axios");
const bitcore = require("bitcore-lib");
const { testnet } = require("bitcore-lib/lib/networks");

const createWallet = () => {
  const privateKey = new bitcore.PrivateKey();
  const address = privateKey.toAddress();
  return {
    privateKey: privateKey.toString(),
    address: address.toString(),
  };
};

const validWalletAddress = async (address) => {
  return bitcore.Address.isValid(address, testnet);
};

const getBalance = async (address) => {
  const isValid = await validWalletAddress(address);

  if (!isValid) {
    return "invalid address";
  }

  const url = `https://api.blockcypher.com/v1/btc/test3/addrs/${address}/balance`;
  const balance = await axios.get(url);

  return balance.data.balance;
};

const send = async (to, value) => {
  const privateKey = new bitcore.PrivateKey(
    "a0e2b014317b443f5fb1708b4193e9be4d9223013ca0ca729ebf0decf476bd91"
  );

  const apiToken = { token };

  let newtx = {
    inputs: [{ addresses: ["mwRoSmns2iWowMmsYAxwDukyTgaDx3qvGj"] }],
    outputs: [{ addresses: [to], value: value }],
  };

  const newTxData = await axios.post(
    `https://api.blockcypher.com/v1/btc/test3/txs/new`,
    JSON.stringify(newtx)
  );

  const tmptx = newTxData.data;
  tmptx.pubkeys = [];
  tmptx.signatures = tmptx.tosign.map((sign, n) => {
    const pubkey = privateKey.toPublicKey().toString("hex");
    tmptx.pubkeys.push(pubkey);
    const signature = bitcore.crypto.ECDSA.sign(
      Buffer.from(sign, "hex"),
      privateKey,
      "big"
    ).toString("hex");
    return signature;
  });

  axios
    .post(
      `https://api.blockcypher.com/v1/btc/test3/txs/send?token=${apiToken}`,
      JSON.stringify(tmptx)
    )
    .then((finaltx) => {
      console.log(finaltx.data);
    })
    .catch((err) => {
      console.log(err);
    });
};

module.exports = {
  createWallet: createWallet,
  getBalance: getBalance,
  send: send,
};
