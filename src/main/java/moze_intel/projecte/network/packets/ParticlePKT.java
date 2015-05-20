package moze_intel.projecte.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ParticlePKT implements IMessage, IMessageHandler<ParticlePKT, IMessage>
{
	private EnumParticleTypes particleName;
	private double x;
	private double y;
	private double z;
	private double velX;
	private double velY;
	private double velZ;

	//Needs to have an empty constructor
	public ParticlePKT() {}
	
	public ParticlePKT(EnumParticleTypes name, double x, double y, double z)
	{
		particleName = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ParticlePKT(EnumParticleTypes name, double x, double y, double z, double velX, double velY, double velZ)
	{
		this(name, x, y, z);
		this.velX = velX;
		this.velY = velY;
		this.velZ = velZ;
	}

	@Override
	public IMessage onMessage(ParticlePKT message, MessageContext ctx) 
	{
		Minecraft.getMinecraft().theWorld.spawnParticle(message.particleName, message.x, message.y, message.z, message.velX, message.velY, message.velZ);
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buffer) 
	{
		particleName = EnumParticleTypes.getParticleFromId(buffer.readInt());
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		velX = buffer.readDouble();
		velY = buffer.readDouble();
		velZ = buffer.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buffer) 
	{
		buffer.writeInt(particleName.getParticleID());
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeDouble(velX);
		buffer.writeDouble(velY);
		buffer.writeDouble(velZ);
	}
}
