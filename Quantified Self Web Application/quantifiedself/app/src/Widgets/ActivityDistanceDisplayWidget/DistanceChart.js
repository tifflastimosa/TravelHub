import {Bar, Doughnut, Line, Radar} from "react-chartjs-2";

const DistanceChart = (props) => {
    return (
        <Bar data={{labels: props.labels, datasets: [{
            label: "distances",
            data: props.data,
            backgroundColor: 'rgb(130,153,239)',
            borderColor: 'rgb(65,96,203)',
                borderWidth: 1
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
                  tension: 0.25
              }} />
    )
}

export default DistanceChart
