Descripción de clases:
Ventana:			
		*Clase donde se encuentra la interfaz grafica
ServiceMulticast:	
		*Clase donde se encuentra el Servidor Multicast que va anunciando su puerto a todos(serviceWrite)
		*Clase donde se encuentra el Servidor que escucha los puertos de los demas servidores(serviceListen)
		*Se encuentran los metodos Servidor y Cliente RMI para pasar los archivos, pero falta por terminar(service_SerRMI,service_CliRMI)
ServerRMI:
		*Servidor RMI, que se encarga de Buscar los archivos en los demas Servidores, falta agregar los datos para hacer la transferencia de archivos
ClientRMI:
		*Cliente RMI, Clase que solo prueba el funcionamiento del Servidor RMI
Archivos:
		*Interfaz RMI, donde se encuentra la definición de los metodos que se pueden llamar del Servidor RMI
ServerFile:
		*Servidor para Enviar archivos, falta ajustarlo para que se ajuste a la aplicación
Nodo:
		*Clase donde se almacenan la información de los nodos que se van anunciando, y a la vez se van ordenando con quicksort