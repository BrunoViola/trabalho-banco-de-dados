import React, { useEffect, useState } from "react";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
    LineElement,
    PointElement,
    Filler,
    ArcElement,
} from 'chart.js';
import { Bar, Line, Pie } from 'react-chartjs-2';
import api from "../Services/api.js";
import "./Graficos.css"

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    LinearScale,
    PointElement,
    LineElement,
    ArcElement,
    Title,
    Tooltip,
    Legend,
    Filler
);

export const optionsGrafico1 = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: '5 autores mais vendidos',
      },
    },
}

export const optionsGrafico2 = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        legend: {
        position: 'top',
        },
        title: {
        display: true,
        text: 'Faixa etária dos clientes',
        },
    },
}

export const optionsGrafico3 = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        legend: {
        position: 'top',
        },
        title: {
        display: true,
        text: 'Gasto médio por sexo',
        },
    },
}

export const optionsGrafico4 = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        legend: {
        position: 'top',
        },
        title: {
        display: true,
        text: '5 localidades que mais compram',
        },
    },
}

export const optionsGrafico5 = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        legend: {
        position: 'top',
        },
        title: {
        display: true,
        text: 'Valor Médio Estoque x Valor Médio Compras',
        },
    },
}

export const optionsGrafico6 = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        legend: {
        position: 'top',
        },
        title: {
        display: true,
        text: 'Vendas por faixa de preço',
        },
    },
}

export const optionsGrafico7 = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
      legend: {
      position: 'top',
      },
      title: {
      display: true,
      text: 'Vendas dos últimos 6 meses',
      },
  },
}

const Graficos = () => {
  const [grafico1, setGrafico1] = useState(null);
  const [grafico2, setGrafico2] = useState(null);
  const [grafico3, setGrafico3] = useState(null);
  const [grafico4, setGrafico4] = useState(null);
  const [grafico5, setGrafico5] = useState(null);
  const [grafico6, setGrafico6] = useState(null);
  const [grafico7, setGrafico7] = useState(null);

  useEffect(() => {
    getData();
  }, []);

  const getData = async () => {
    const resultGrafico1 = await api.get("/dados/grafico1");
    const labelsGrafico1 = resultGrafico1?.data?.map(element => { return String(element.nomeAutor) }) //Alterar
    const valuesGrafico1 = resultGrafico1?.data?.map(element => { return String(element.quantidadeLivrosAutores) }) //Alterar

    const dataGrafico1 = {
      labels: labelsGrafico1,
      datasets: [
        {
          fill: true,
          label: 'Quantidade',
          data: valuesGrafico1,
          borderColor: 'rgb(53, 162, 235)',
          backgroundColor: 'rgba(53, 162, 235, 0.5)',
        },
      ],
    }
    setGrafico1(dataGrafico1);

    const resultGrafico2 = await api.get("/dados/grafico2");
    const labelsGrafico2 = resultGrafico2.data.map(element => { return String(element.faixaEtaria) });
    const valuesGrafico2 = resultGrafico2.data.map(element => { return String(element.quantidadePessoasFaixas) });

    const dataGrafico2 = {
      labels: labelsGrafico2,
      datasets: [
        {
          data: valuesGrafico2,
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(201, 203, 207, 0.2)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(201, 203, 207, 1)'
          ],
        
          borderWidth: 1,
        },
      ],
    }
    setGrafico2(dataGrafico2);

    const resultGrafico3 = await api.get("/dados/grafico3");
    const labelsGrafico3 = resultGrafico3?.data?.map(element => { return String(element.sexo) })
    const valuesGrafico3 = resultGrafico3?.data?.map(element => { return String(element.gastoMedioSexo) })

    const dataGrafico3 = {
        labels: labelsGrafico3,
        datasets: [
        {
            fill: true,
            label: 'Gasto médio',
            data: valuesGrafico3,
            borderColor: 'rgba(211, 84, 0, 1)',
            backgroundColor: 'rgba(243, 156, 18, 0.6)',
        },
        ],
    }
    setGrafico3(dataGrafico3);

    const resultGrafico4 = await api.get("/dados/grafico4");
    const labelsGrafico4 = resultGrafico4?.data?.map(element => { return String(element.localizacao) })
    const valuesGrafico4 = resultGrafico4?.data?.map(element => { return String(element.totalGastoLocalidade) })

    const dataGrafico4 = {
        labels: labelsGrafico4,
        datasets: [
        {
            fill: true,
            label: 'Total gasto',
            data: valuesGrafico4,
            borderColor: 'rgba(39, 174, 96, 1)',
            backgroundColor: 'rgba(46, 204, 113, 0.6)',
        },
        ],
    }
    setGrafico4(dataGrafico4);

    const resultGrafico5 = await api.get("/dados/grafico5");
    const labelsGrafico5 = resultGrafico5.data?.map(element => { return String(element.secaoNome) });
    const precoMedioEstoque5 = resultGrafico5.data?.map(element => { return String(element.precoMedioEstoque) });
    const DprecoMedioCompras5 = resultGrafico5.data?.map(element => { return String(element.precoMedioCompras) });
    
    const dataGrafico5 = {
      labels: labelsGrafico5,
      datasets: [
        {
          label: 'Preço médio estoque',
          data: precoMedioEstoque5,
          backgroundColor: 'rgba(255, 99, 132, 0.5)',
        },
        {
          label: 'Preço médio compras',
          data: DprecoMedioCompras5,
          backgroundColor: 'rgba(53, 162, 235, 0.5)',
        },
      ],
    };
    setGrafico5(dataGrafico5);

    const resultGrafico6 = await api.get("/dados/grafico6");
    const labelsGrafico6 = resultGrafico6?.data?.map(element => { return String(element.faixaPreco) })
    const valuesGrafico6 = resultGrafico6?.data?.map(element => { return String(element.unidadesVendidasFaixa) })

    const dataGrafico6 = {
      labels: labelsGrafico6,
      datasets: [
        {
          fill: true,
          label: 'Quantidade',
          data: valuesGrafico6,
          borderColor: 'rgba(142, 68, 173, 1)',
          backgroundColor: 'rgba(155, 89, 182, 0.6)',
        },
      ],
    }
    setGrafico6(dataGrafico6);

    const resultGrafico7 = await api.get("/dados/grafico7");
    const labelsGrafico7 = resultGrafico7?.data?.map(element => { return String(element.mes) })
    const valuesGrafico7 = resultGrafico7?.data?.map(element => { return String(element.totalVendasMes) })

    const dataGrafico7 = {
      labels: labelsGrafico7,
      datasets: [
        {
          fill: true,
          label: 'Total de Vendas',
          data: valuesGrafico7,
          borderColor: 'rgba(112, 112, 112, 1)',
          backgroundColor: 'rgba(211, 211, 211, 0.5)',
        },
      ],
    }
    setGrafico7(dataGrafico7);
  }
      
    //Fazer para demais gráficos

  return (
    <>
      <h2>Dashboard</h2>
      <div className="div-table">
        <div className="row r">
          <div className="col-lg-6 chart">
            {grafico1 && <Bar options={optionsGrafico1} data={grafico1} />}
          </div>
          <div className="col-lg-6 chart">
            {grafico2 && <Pie options={optionsGrafico2} data={grafico2} />}
          </div>
        </div>
        <div className="row r">
          <div className="col-lg-6 chart">
            {grafico3 && <Bar options={optionsGrafico3} data={grafico3} />}
          </div>
          <div className="col-lg-6 chart">
            {grafico4 && <Bar options={optionsGrafico4} data={grafico4} />}
          </div>
        </div>
        <div className="row r">
          <div className="col-lg-6 chart">
            {grafico5 && <Bar options={optionsGrafico5} data={grafico5} />}
          </div>
          <div className="col-lg-6 chart">
            {grafico6 && <Bar options={optionsGrafico6} data={grafico6} />}
          </div>
        </div>
        <div className="row r">
          <div className="col-lg-12 chart">
            {grafico7 && <Line options={optionsGrafico7} data={grafico7} />}
          </div>
        </div>
      </div>
    </>
  )
  /*return (
    <>
      <h2>Dashboard</h2>
      <div className="div-table">
        <div className="row">
          <div className="col-lg-6">
            {grafico1 && <Line options={optionsGrafico1} data={grafico1} />}
          </div>
          <div className="col-lg-6">
            {grafico2 && <Pie options={optionsGrafico2} data={grafico2} />}
          </div>
        </div>
        <div className="row">
          <div className="col-lg-6">
            {grafico3 && <Bar options={optionsGrafico3} data={grafico3} />}
          </div>
          <div className="col-lg-6">
            {grafico4 && <Bar options={optionsGrafico4} data={grafico4} />}
          </div>
        </div>
      </div>
    </>
  )*/

};

export default Graficos;
