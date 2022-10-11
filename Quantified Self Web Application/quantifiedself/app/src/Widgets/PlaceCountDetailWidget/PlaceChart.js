import {Bar, Doughnut, Line, Radar} from "react-chartjs-2";

const PlaceChart = (props) => {
    return (
        <Line data={{labels: props.labels, datasets: [{
                label: "places",
                data: props.count,
                backgroundColor: 'rgb(234,112,109)',
                borderColor: 'rgb(234,112,109)',
            }]}}
              options={{
                  responsive:true,
                  title: { text: "This is a DistanceChart", display: true },
                  scales:{
                      yAxes:[ {
                          ticks:{
                              beginAtZero: true
                          }
                      }
                      ]
                  },
                  tension: 0.25,
                  plugins: {
                  }
              }} />
    )
}

export default PlaceChart
